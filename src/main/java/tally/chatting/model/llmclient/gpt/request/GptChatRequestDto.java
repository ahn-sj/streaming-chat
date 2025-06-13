package tally.chatting.model.llmclient.gpt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmModel;
import tally.chatting.model.llmclient.gpt.GptMessageRole;

import java.util.List;
import java.util.Optional;

/**
 * gpt4.1 Streaming Request Example
 * <p>
 * curl https://api.openai.com/v1/chat/completions \
 * -H "Content-Type: application/json" \
 * -H "Authorization: Bearer $OPENAI_API_KEY" \
 * -d '{
 * "model": "gpt-4.1",
 * "messages": [
 * {
 * "role": "developer",
 * "content": "You are a helpful assistant."
 * },
 * {
 * "role": "user",
 * "content": "Hello!"
 * }
 * ],
 * "stream": true
 * }'
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChatRequestDto {
    private List<GptCompletionRequestDto> messages;
    private LlmModel model;
    private Boolean stream;
    private GptResponseFormat response_format;

    public GptChatRequestDto(final LlmChatRequestDto llmChatRequestDto) {
        final boolean isPresent = Optional.ofNullable(llmChatRequestDto.getUseJson())
                .filter(useJson -> useJson)
                .isPresent();

        if (isPresent) {
            response_format = new GptResponseFormat("json_object");
        }
        this.messages = List.of(
                new GptCompletionRequestDto(GptMessageRole.SYSTEM, llmChatRequestDto.getSystemPrompt()),
                new GptCompletionRequestDto(GptMessageRole.USER, llmChatRequestDto.getUserRequest())
        );
        this.model = llmChatRequestDto.getLlmModel();
    }
}
