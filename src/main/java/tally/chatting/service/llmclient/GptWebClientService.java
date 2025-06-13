package tally.chatting.service.llmclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;

import static tally.chatting.util.TodoUtils.TODO;

@Service
@RequiredArgsConstructor
public class GptWebClientService implements LlmWebClientService {

    private final WebClient webClient;
    @Value("${llm.gpt.key}")
    private String gptApiKey;

    /**
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
     *     ]
     *   }'
     *
     * @param llmChatRequestDto LLM 요청 DTO
     * @return Mono<LlmChatResponseDto> LLM 응답 DTO
     */
    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(final LlmChatRequestDto llmChatRequestDto) {
        TODO("WebClient로 LLM API 호출 구현");


        return null;
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }
}
