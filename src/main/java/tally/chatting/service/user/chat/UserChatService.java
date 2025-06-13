package tally.chatting.service.user.chat;

import reactor.core.publisher.Mono;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;

public interface UserChatService {
    Mono<UserChatResponseDto> getOneShotChat(UserChatRequestDto userChatRequestDto);
}
