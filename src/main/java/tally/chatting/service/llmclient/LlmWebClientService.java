package tally.chatting.service.llmclient;

import reactor.core.publisher.Mono;
import tally.chatting.model.llmclient.LlmChatRequestDto;
import tally.chatting.model.llmclient.LlmChatResponseDto;
import tally.chatting.model.llmclient.LlmType;

public interface LlmWebClientService {
    Mono<LlmChatResponseDto> getChatCompletion(LlmChatRequestDto llmChatRequestDto);

    /**
     * gptWebClientService, geminiWebClientService 등에서 이 메서드를 구현하여 LlmType을 반환함
     *
     * @return LlmType
     */
    LlmType getLlmType();
}
