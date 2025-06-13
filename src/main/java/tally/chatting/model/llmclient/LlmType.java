package tally.chatting.model.llmclient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LlmType {
    GPT("gpt"),
    GEMINI("gemini"),
    ;

    private final String code;
}
