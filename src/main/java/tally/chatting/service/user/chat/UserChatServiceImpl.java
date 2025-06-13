package tally.chatting.service.user.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;
import tally.chatting.service.llmclient.LlmWebClientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    private final LlmWebClientService llmWebClientService;

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(final UserChatRequestDto userChatRequestDto) {
        final LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(userChatRequestDto, "Plan: 요청에 적절히 응답하기");
        final Mono<LlmChatResponseDto> chatCompletionMono = llmWebClientService.getChatCompletion(llmChatRequestDto);

        return chatCompletionMono.map(UserChatResponseDto::new);
    }
}
