package tally.chatting.model.llmclient.gemini;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeminiMessageRole {
    USER,
    MODEL,
    ;

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
