package tally.chatting.service.user.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChainOfThoughtServiceImpl implements ChainOfThoughtService {

    @Override
    public Flux<UserChatResponseDto> getChainOfThoughtResponse(final UserChatRequestDto userChatRequestDto) {
        return null;
    }
}
