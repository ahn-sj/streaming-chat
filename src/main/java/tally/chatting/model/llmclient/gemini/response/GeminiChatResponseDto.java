package tally.chatting.model.llmclient.gemini.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeminiChatResponseDto {
    private List<GeminiCandidate> candidates;

    public String getFirstText() {
        return candidates.stream()
                .findFirst()
                .flatMap(candidate -> {
                    final GeminiContent content = candidate.getContent();
                    final List<GeminiPart> parts = content.getParts();

                    return parts.stream()
                            .findFirst()
                            .map(GeminiPart::getText);
                })
                .orElseThrow();
    }

}
