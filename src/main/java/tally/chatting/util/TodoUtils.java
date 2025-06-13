package tally.chatting.util;

public final class TodoUtils {
    private TodoUtils() {}

    public static TodoException TODO(final String message) {
            return new TodoException(message);
        }

    public static class TodoException extends RuntimeException {
        public TodoException(String message) {
            super(message);
        }
    }
}

