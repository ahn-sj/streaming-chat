package tally.chatting.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatUtils {
    public static String extractJsonString(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        int startIndex = content.indexOf("{");
        int endIndex = content.lastIndexOf("}");

        // JSON 문자열이 시작하거나 끝나는 위치가 없거나, 시작 위치가 끝 위치보다 뒤에 있는 경우
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            log.error("Invalid JSON format in content: {}", content);
            return "";
        }

        return content.substring(startIndex, endIndex + 1);

    }
}
