package tally.chatting.service.llmclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;
import tally.chatting.model.llmclient.gpt.response.GptChatResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiWebClientService implements LlmWebClientService {

    private final WebClient webClient;
    @Value("${llm.gemini.key}")
    private String geminiApiKey;

    @Override
    public Mono<LlmChatResponseDto> getChatCompletion(final LlmChatRequestDto llmChatRequestDto) {
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key= + " + geminiApiKey)
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
    public LlmType getLlmType() {
        return LlmType.GEMINI;
    }
}
