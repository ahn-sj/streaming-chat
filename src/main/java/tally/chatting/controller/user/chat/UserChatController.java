package tally.chatting.controller.user.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tally.chatting.model.user.chat.UserChatRequestDto;
import tally.chatting.model.user.chat.UserChatResponseDto;
import tally.chatting.service.user.chat.ChainOfThoughtService;
import tally.chatting.service.user.chat.UserChatService;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class UserChatController {

    private final UserChatService userChatService;
    private final ChainOfThoughtService chainOfThoughtService;

    @PostMapping("/oneshot")
    public Mono<UserChatResponseDto> oneShotChat(
            @RequestBody UserChatRequestDto userChatRequestDto
    ) {
        return userChatService.getOneShotChat(userChatRequestDto);
    }

    @PostMapping("/oneshot/stream")
    public Flux<UserChatResponseDto> oneShotChatSteam(
            @RequestBody UserChatRequestDto userChatRequestDto
    ) {
        return userChatService.getOneShotChatStream(userChatRequestDto);
    }

    @PostMapping("/cot")
    public Flux<UserChatResponseDto> chainOfThought(
            @RequestBody UserChatRequestDto userChatRequestDto
    ) {
        return chainOfThoughtService.getChainOfThoughtResponse(userChatRequestDto);
    }
}
