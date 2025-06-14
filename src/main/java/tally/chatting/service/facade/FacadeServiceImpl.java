package tally.chatting.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tally.chatting.model.facade.FacadeAvailableModel;
import tally.chatting.model.facade.FacadeHomeResponseDto;
import tally.chatting.model.llmclient.LlmModel;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeServiceImpl implements FacadeService {
    @Override
    public Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto() {
        return Mono.fromCallable(() -> {
            final List<FacadeAvailableModel> availableModelList = Arrays.stream(LlmModel.values())
                    .map(FacadeAvailableModel::new)
                    .toList();
            return new FacadeHomeResponseDto(availableModelList);
        });
    }
}
