package tally.chatting.model.llmclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.user.chat.UserChatRequestDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LlmChatRequestDto {
    private String userRequest;
    private String systemPrompt; // userRequest 에 포함되는 내용보다 더 높은 강제성과 우선순위를 가짐
    private LlmModel llmModel;
    private Boolean useJson;

    public LlmChatRequestDto(final UserChatRequestDto userChatRequestDto, final String systemPrompt) {
        this.llmModel = userChatRequestDto.getLlmModel();
        this.systemPrompt = systemPrompt;
        this.userRequest = userChatRequestDto.getRequest();
    }
}
