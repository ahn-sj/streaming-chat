package tally.chatting.controller.user.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class UserChatController {

    @PostMapping("/oneshot")
    public Mono<UserChatResponseDto> oneShotChat(
            @RequestBody UserChatRequestDto userChatRequestDto
    ) {
        return Mono.empty();
    }

}
