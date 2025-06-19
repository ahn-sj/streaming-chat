package tally.chatting.model.llmclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.exception.CommonError;
import tally.chatting.model.llmclient.gemini.response.GeminiChatResponseDto;
import tally.chatting.model.llmclient.gpt.response.GptChatResponseDto;
import tally.chatting.model.llmclient.gpt.response.GptChoice;
import tally.chatting.model.llmclient.gpt.response.GptResponseMessageDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LlmChatResponseDto {
    private String llmResponse;
    private String title;
    private CommonError error;

    public LlmChatResponseDto(final String llmResponse) {
        this.llmResponse = llmResponse;
    }

    public LlmChatResponseDto(final CommonError error) {
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
