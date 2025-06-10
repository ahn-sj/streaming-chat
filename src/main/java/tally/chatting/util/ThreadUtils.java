package tally.chatting.util;

public final class ThreadUtils {
    private ThreadUtils() {
        // Utility class, prevent instantiation
    }

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new RuntimeException("Thread was interrupted", e);
        }
    }
}
