package tally.chatting.service.user.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;

import static tally.chatting.util.TodoUtils.TODO;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    @Override
    public Mono<UserChatResponseDto> getOneShotChat(final UserChatRequestDto userChatRequestDto) {
        TODO("WebClient로 LLM API 호출 구현");

        return null;
    }
}
