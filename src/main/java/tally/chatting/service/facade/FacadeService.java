package tally.chatting.service.facade;

import reactor.core.publisher.Mono;
import tally.chatting.model.facade.FacadeHomeResponseDto;

public interface FacadeService {
    Mono<FacadeHomeResponseDto> getFacadeHomeResponseDto();
}
