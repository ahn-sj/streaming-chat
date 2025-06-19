package tally.chatting.service.user.chat;

import reactor.core.publisher.Flux;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;

public interface ChainOfThoughtService {
    Flux<UserChatResponseDto> getChainOfThoughtResponse(final UserChatRequestDto userChatRequestDto);
}
