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
    private String title;
    private String response;
    private CommonError error;

    public UserChatResponseDto(final LlmChatResponseDto chatResponseDto) {
        this.title = chatResponseDto.getTitle();
        this.response = chatResponseDto.getLlmResponse();
        this.error = chatResponseDto.getError();
    }

    public UserChatResponseDto(final String title, final String response) {
        this.title = title;
        this.response = response;
        this.error = null; // 기본적으로 에러는 null로 설정
    }
}
