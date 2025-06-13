package tally.chatting.model.llmclient.gpt;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GptMessageRole {
    SYSTEM("시스템 프롬프트"),
    USER("유저 입력"),
    ASSISTANT("AI 응답"),
    ;

    private final String description;

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
