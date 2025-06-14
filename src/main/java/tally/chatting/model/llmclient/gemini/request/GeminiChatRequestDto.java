package tally.chatting.model.llmclient.gemini.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.gemini.GeminiMessageRole;
import tally.chatting.model.llmclient.gemini.response.GeminiContent;
import tally.chatting.model.llmclient.gemini.response.GeminiPart;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeminiChatRequestDto {
    private List<GeminiContent> contents;
    private GeminiContent systemInstruction;
    private GeminiGenerationConfigDto generationConfig;

    public GeminiChatRequestDto(final LlmChatRequestDto llmChatRequestDto) {
        final boolean isPresent = Optional.ofNullable(llmChatRequestDto.getUseJson())
                .filter(useJson -> useJson)
                .isPresent();
        if(isPresent) {
            this.generationConfig = new GeminiGenerationConfigDto();
        }
        this.contents = List.of(new GeminiContent(List.of(new GeminiPart(llmChatRequestDto.getUserRequest())), GeminiMessageRole.USER));
        this.systemInstruction = new GeminiContent(List.of(new GeminiPart(llmChatRequestDto.getSystemPrompt())));
    }
}
