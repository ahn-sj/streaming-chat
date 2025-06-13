package tally.chatting.model.llmclient.gpt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptCompletionRequestDto {
    private String role; // 메시지의 역할 (예: "user", "assistant", "system")
    private String content; // 채팅 내용
}
