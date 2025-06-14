package tally.chatting.exception;

public class ErrorTypeException extends RuntimeException {
    private final CustomErrorType errorType;

    public ErrorTypeException(final CustomErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorTypeException(final String message, final CustomErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public CustomErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        return "Code: " + errorType.getCode() + ", Message: " + super.getMessage();
    }
}
