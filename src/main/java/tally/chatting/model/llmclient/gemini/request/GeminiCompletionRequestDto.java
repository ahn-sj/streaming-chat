package tally.chatting.model.llmclient.gemini.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.gemini.GeminiMessageRole;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeminiCompletionRequestDto {
    private GeminiMessageRole role; // 메시지의 역할 (예: "user", "model")
    private String content; // 채팅 내용

    public GeminiCompletionRequestDto(final String systemPrompt) {
        this.content = systemPrompt; // 시스템 프롬프트 내용 설정
    }
}
