package tally.chatting.model.llmclient.gpt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.LlmModel;

import java.util.List;

/**
 * gpt4.1 Streaming Request Example
 *
 * curl https://api.openai.com/v1/chat/completions \
 *   -H "Content-Type: application/json" \
 *   -H "Authorization: Bearer $OPENAI_API_KEY" \
 *   -d '{
 *     "model": "gpt-4.1",
 *     "messages": [
 *       {
 *         "role": "developer",
 *         "content": "You are a helpful assistant."
 *       },
 *       {
 *         "role": "user",
 *         "content": "Hello!"
 *       }
 *     ],
 *     "stream": true
 *   }'
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptChatRequestDto {
    private List<GptCompletionRequestDto> messages;
    private LlmModel llmModel;
    private Boolean stream;
}
