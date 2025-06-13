package tally.chatting.model.user.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.LlmChatResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserChatResponseDto {
    private String response;

    public UserChatResponseDto(final LlmChatResponseDto chatCompletion) {
        this.response = chatCompletion.getLlmResponse();
    }
}
