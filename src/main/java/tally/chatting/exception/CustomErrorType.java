package tally.chatting.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorType {
    GEMINI_RESPONSE_ERROR(1),
    GPT_RESPONSE_ERROR(2),
    ;

    private final int code;
}
