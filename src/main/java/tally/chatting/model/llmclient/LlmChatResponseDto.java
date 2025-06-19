package tally.chatting.model.llmclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tally.chatting.exception.CommonError;
import tally.chatting.model.llmclient.gemini.response.GeminiChatResponseDto;
import tally.chatting.model.llmclient.gpt.response.GptChatResponseDto;
import tally.chatting.model.llmclient.gpt.response.GptChoice;
import tally.chatting.model.llmclient.gpt.response.GptResponseMessageDto;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class LlmChatResponseDto {
    private String llmResponse;
    private String title;
    private CommonError error;

    public boolean isValid() {
        return Optional.ofNullable(error).isEmpty();
    }

    public LlmChatResponseDto(final String llmResponse) {
        this.llmResponse = llmResponse;
    }

    public LlmChatResponseDto(final CommonError error) {
        log.error("LlmChatResponseDto error: {}", error);
        this.error = error;
    }

    public LlmChatResponseDto(final CommonError error, Throwable throwable) {
        log.error("LlmChatResponseDto error: {}", error, throwable);
        this.error = error;
    }

    public LlmChatResponseDto(final String title, final String llmResponse) {
        this.title = title;
        this.llmResponse = llmResponse;
    }

    public LlmChatResponseDto(final GptChatResponseDto gptChatResponseDto) {
        final GptChoice choice = gptChatResponseDto.getFirstChoice();
        final GptResponseMessageDto message = choice.getMessage();
        this.llmResponse = message.getContent();
    }

    public LlmChatResponseDto(final GeminiChatResponseDto geminiChatResponseDto) {
        this.llmResponse = geminiChatResponseDto.getFirstText();
    }

    public static LlmChatResponseDto getLlmChatResponseDtoFromStream(final GptChatResponseDto gptChatResponseDto) {
        final GptChoice choice = gptChatResponseDto.getFirstChoice();
        final GptResponseMessageDto message = choice.getDelta();
        return new LlmChatResponseDto(message.getContent());
    }
}
