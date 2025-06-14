package tally.chatting.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonError {
    private String errorCode;
    private String errorMessage;

    public CommonError(final int code, final String message) {
        this.errorCode = String.valueOf(code);
        this.errorMessage = message;
    }
}
