package tally.chatting.model.llmclient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LlmModel {
    GPT_4O("gpt-4o", LlmType.GPT),
    GEMINI_2_0_FLASH("gemini-2.0-flash", LlmType.GEMINI),
    ;

    private final String code;
    private final LlmType llmType;
}
