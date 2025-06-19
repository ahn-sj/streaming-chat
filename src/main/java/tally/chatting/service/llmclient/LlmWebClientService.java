package tally.chatting.service.llmclient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tally.chatting.exception.CommonError;
import tally.chatting.exception.ErrorTypeException;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;

public interface LlmWebClientService {
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto llmChatRequestDto);

    Flux<LlmChatResponseDto> getChatCompletionStream(LlmChatRequestDto llmChatRequestDto);

    /**
     * gptWebClientService, geminiWebClientService 등에서 이 메서드를 구현하여 LlmType을 반환함
     *
     * @return LlmType
     */
    LlmType getLlmType();

    default Mono<LlmChatResponseDto> getChatCompletionWithCatchException(LlmChatRequestDto requestDto) {
        return getChatCompletion(requestDto)
                .onErrorResume(exception -> {
                    if (exception instanceof ErrorTypeException errorTypeException) {
                        final CommonError commonError = new CommonError(
                                errorTypeException.getErrorType().getCode(),
                                errorTypeException.getMessage()
                        );
                        return Mono.just(new LlmChatResponseDto(commonError));
                    } else {
                        final CommonError commonError = new CommonError(
                                500,
                                exception.getMessage()
                        );
                        return Mono.just(new LlmChatResponseDto(commonError));
                    }
                });
    }

}
