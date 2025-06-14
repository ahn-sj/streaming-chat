package tally.chatting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tally.chatting.model.llmclient.LlmType;
import tally.chatting.service.llmclient.LlmWebClientService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CommonConfig {

    @Bean
    public Map<LlmType, LlmWebClientService> getLlmWebClientServiceMap(
            final List<LlmWebClientService> llmWebClientServices
    ) {
        return llmWebClientServices
                .stream()
                .collect(Collectors.toMap(LlmWebClientService::getLlmType, service -> service));
    }
}
