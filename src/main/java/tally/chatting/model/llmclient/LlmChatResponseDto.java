package tally.chatting.model.llmclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.gpt.response.GptChatResponseDto;
import tally.chatting.model.llmclient.gpt.response.GptChoice;
import tally.chatting.model.llmclient.gpt.response.GptResponseMessageDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LlmChatResponseDto {
    private String llmResponse;

    public LlmChatResponseDto(final GptChatResponseDto gptChatResponseDto) {
        final GptChoice choice = gptChatResponseDto.getFirstChoice();
        final GptResponseMessageDto message = choice.getMessage();
        this.llmResponse = message.getContent();
    }
}
