package tally.chatting.service.user.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tally.chatting.exception.CustomErrorType;
import tally.chatting.exception.ErrorTypeException;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmModel;
import tally.chatting.model.llmclient.LlmType;
import tally.chatting.model.llmclient.jsonformat.AnswerListResponseDto;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;
import tally.chatting.service.llmclient.LlmWebClientService;

import java.util.Map;

import static tally.chatting.util.ChatUtils.extractJsonString;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChainOfThoughtServiceImpl implements ChainOfThoughtService {

    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;
    private final ObjectMapper objectMapper;

    /*
        1. 사용자의 요청을 분석
        2. 분석 단계별로 LLM에게 분석 요청
        3. 각 단계별 분석 결과를 종합하여 최종 응답
         */
    @Override
    public Flux<UserChatResponseDto> getChainOfThoughtResponse(final UserChatRequestDto userChatRequestDto) {
        return Flux.create(sink -> {
            final String userRequest = userChatRequestDto.getRequest();
            final LlmModel requestModel = userChatRequestDto.getLlmModel();

            final String establishingThoughtChainPrompt = String.format("""
                    다음은 사용자의 입력입니다: "%s"
                    사용자에게 체계적으로 답변하기 위해 어떤 단계들이 필요할지 정리해주세요.
                    """, userRequest);

            final String establishingThoughtChainSystemPrompt = """
                    아래처럼 List<String> answerList의 형태를 가지는 JSON FORMAT으로 응답해주세요
                    <JSONSCHEMA>
                    {
                        "answerList": ["", ...]
                    }
                    </JSONSCHEMA>
                    """;

            final LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(
                    establishingThoughtChainPrompt,
                    establishingThoughtChainSystemPrompt,
                    requestModel,
                    true
            );

            final LlmWebClientService llmWebClientService = llmWebClientServiceMap.get(requestModel.getLlmType());
            final Mono<AnswerListResponseDto> cotStepListMono = llmWebClientService.getChatCompletion(llmChatRequestDto)
                    .map(response -> {
                        final String llmResponse = response.getLlmResponse();
                        log.info("[GEMINI][LLM] response = {}", llmResponse);
                        final String extractedJsonString = extractJsonString(llmResponse);
                        try {
                            final AnswerListResponseDto answerListResponseDto = objectMapper.readValue(extractedJsonString, AnswerListResponseDto.class);
//                            sink.next(new UserChatResponseDto("필요한 작업 단계 분석", answerListResponseDto.toString()));
                            return answerListResponseDto;
                        } catch (JsonProcessingException e) {
                            throw new ErrorTypeException("[JSON 파싱 오류] input = {}" + extractedJsonString, CustomErrorType.LLM_RESPONSE_JSON_PARSING_ERROR);
                        }
                    })
                    .doOnNext(publishedData -> sink.next(new UserChatResponseDto("필요한 작업 단계 분석", publishedData.toString())));


            final Flux<String> cotStepFlux = cotStepListMono.flatMapMany(cotStepList -> Flux.fromIterable(cotStepList.getAnswerList()));

            final Flux<String> analyzedCotStep = cotStepFlux.flatMapSequential(cotStep -> {
                        final String cotStepPrompt = String.format("""
                                다음은 사용자의 입력입니다: "%s"
                                사용자의 요구를 다음 단계에 따라 분석해주세요: "%s"
                                """, userRequest, cotStep);

                        final LlmChatRequestDto cotStepRequestDto = new LlmChatRequestDto(
                                cotStepPrompt,
                                "",
                                requestModel,
                                false
                        );
                        return llmWebClientService.getChatCompletionWithCatchException(cotStepRequestDto)
                                .map(LlmChatResponseDto::getLlmResponse);
                    })
                    .doOnNext(publishedData -> sink.next(new UserChatResponseDto("단계별 분석 결과", publishedData)));

            final Mono<String> finalAnswerMono = analyzedCotStep.collectList()
                    .flatMap(stepPromptList -> {
                        final String concatStepPrompt = String.join("\n", stepPromptList);
                        final String finalAnswerPrompt = String.format("""
                                다음은 사용자의 입력입니다: "%s
                                아래 사항들을 참고, 분석하여 사용자의 입력에 대한 최종 답변을 해주세요:
                                %s
                                """, userRequest, concatStepPrompt);
                        final LlmChatRequestDto requestDto = new LlmChatRequestDto(finalAnswerPrompt, "", requestModel, false);
                        return llmWebClientService.getChatCompletionWithCatchException(requestDto)
                                .map(LlmChatResponseDto::getLlmResponse);
                    });

            finalAnswerMono.subscribe(finalAnswer -> {
                sink.next(new UserChatResponseDto("최종 응답", finalAnswer));
                sink.complete();
            }, error -> {
                log.error("[COT] cot response error", error);
                sink.error(error);
            });

        });


    }
}

