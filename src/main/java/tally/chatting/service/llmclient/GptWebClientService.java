package tally.chatting.service.llmclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;
import tally.chatting.model.llmclient.gpt.request.GptChatRequestDto;
import tally.chatting.model.llmclient.gpt.response.GptChatResponseDto;

import java.util.Optional;

import static tally.chatting.util.TodoUtils.TODO;

@Slf4j
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
     * @param requestDto LLM 요청 DTO
     * @return Mono<LlmChatResponseDto> LLM 응답 DTO
     */
    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(final LlmChatRequestDto requestDto) {
        final GptChatRequestDto gptChatRequestDto = new GptChatRequestDto(requestDto);

        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(gptChatRequestDto)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        clientResponse -> {
                            return clientResponse.bodyToMono(String.class).flatMap(body -> {
                                log.error("Error Response from GPT API: {}", body);
                                return Mono.error(new RuntimeException("API request failed" + body));
                            });
                        }
                )
                .bodyToMono(GptChatResponseDto.class)
                .map(LlmChatResponseDto::new);
    }

    @Override
    public Flux<LlmChatResponseDto> getChatCompletionStream(final LlmChatRequestDto requestDto) {
        final GptChatRequestDto gptChatRequestDto = new GptChatRequestDto(requestDto);
        gptChatRequestDto.setStream(true);

        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(gptChatRequestDto)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        clientResponse -> {
                            return clientResponse.bodyToMono(String.class).flatMap(body -> {
                                log.error("Error Response from GPT API: {}", body);
                                return Mono.error(new RuntimeException("API request failed" + body));
                            });
                        }
                )
                .bodyToFlux(GptChatResponseDto.class)
                .takeWhile(chunk -> Optional.ofNullable(chunk.getFirstChoice().getFinish_reason()).isEmpty())
                .map(LlmChatResponseDto::getLlmChatResponseDtoFromStream);
    }

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }
}
