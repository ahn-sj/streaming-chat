package tally.chatting.controller.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tally.chatting.model.facade.FacadeHomeResponseDto;
import tally.chatting.service.facade.FacadeService;

@RestController
@RequestMapping("/facade")
@RequiredArgsConstructor
public class FacadeController {

    private final FacadeService facadeService;

    @PostMapping("/home")
    public Mono<FacadeHomeResponseDto> homeFacade() {
        return facadeService.getFacadeHomeResponseDto();
    }

}
