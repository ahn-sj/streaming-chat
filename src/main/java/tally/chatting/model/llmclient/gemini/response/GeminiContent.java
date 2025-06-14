package tally.chatting.model.llmclient.gemini.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.gemini.GeminiMessageRole;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeminiContent {
    private List<GeminiPart> parts; // Assuming GeminiPart is a class that represents the content parts of the response
    private GeminiMessageRole role;

    public GeminiContent(final List<GeminiPart> parts) {
        this.parts = parts;
    }
}
