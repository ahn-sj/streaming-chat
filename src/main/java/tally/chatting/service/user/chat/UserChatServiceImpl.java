package tally.chatting.service.user.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;
import tally.chatting.service.llmclient.LlmWebClientService;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    private final Map<LlmType, LlmWebClientService> llmWebClientServiceMap;

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(final UserChatRequestDto userChatRequestDto) {
        return Mono.defer(() -> {
            final LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "Plan: 요청에 적절히 응답하기");
            final LlmWebClientService llmWebClientService = llmWebClientServiceMap.get(userChatRequestDto.getLlmModel().getLlmType());
            final Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientService.getChatCompletion(llmChatRequestDto);

            return chatCompletionMono.map(UserChatResponseDto::new);
        });
    }

    @Override
    public Flux<UserChatResponseDto> getOneShotChatStream(final UserChatRequestDto userChatRequestDto) {
        return Flux.defer(() -> {
            final LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "Plan: 요청에 적절히 응답하기");
            final LlmWebClientService llmWebClientService = llmWebClientServiceMap.get(userChatRequestDto.getLlmModel().getLlmType());
            final Flux<LlmChatResponseDto> chatCompletionMono = llmWebClientService.getChatCompletionStream(llmChatRequestDto);

            return chatCompletionMono.map(UserChatResponseDto::new);
        });
    }
}
