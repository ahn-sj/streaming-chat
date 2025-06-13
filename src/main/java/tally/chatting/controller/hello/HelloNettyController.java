package tally.chatting.controller.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("/reactive")
public class HelloNettyController {

    @GetMapping("/onenine/list")
    public List<Integer> produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sink.add(i);
        }
        return sink;
    }

    /**
     * GET http://localhost:8080/onenine/flux
     * Accept: text/event-stream
     */
    @GetMapping("/onenine/flux")
    public Flux<Integer> produceOneToNineReactive() {
        return Flux.<Integer>create(sink -> {
            for (int i = 1; i <= 9; i++) {
                try {
                    // 2025-06-13T15:01:13.386+09:00  INFO 9007 --- [chatting] [oundedElastic-1] t.c.controller.HelloNettyController      : [Thread][boundedElastic-1]
                    log.info("[Thread][{}]", Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sink.next(i);
            }
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
