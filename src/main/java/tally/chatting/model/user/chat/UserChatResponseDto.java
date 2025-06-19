package tally.chatting.model.user.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.exception.CommonError;
import tally.chatting.model.llmclient.LlmChatResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserChatResponseDto {
    private String response;
    private String title;
    private CommonError error;

    public UserChatResponseDto(final LlmChatResponseDto chatResponseDto) {
        this.response = chatResponseDto.getLlmResponse();
        this.title = chatResponseDto.getTitle();
        this.error = chatResponseDto.getError();
    }
}
