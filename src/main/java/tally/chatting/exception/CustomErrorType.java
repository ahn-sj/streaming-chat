package tally.chatting.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorType {
    GEMINI_RESPONSE_ERROR(1),
    GPT_RESPONSE_ERROR(2),
    LLM_RESPONSE_JSON_PARSING_ERROR(3);

    private final int code;
}
