package tally.chatting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static tally.chatting.util.ThreadUtils.sleep;

@SpringBootTest
public class SubscriberPublisherAsyncTest {

    @Test
    public void produceOneToNineFlux() {
        Flux<Integer> intFlux = Flux.<Integer>create(sink -> {
            for (int i = 1; i <= 9; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sink.next(i);
            }
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic());

        System.out.println("[Thread Name = " + Thread.currentThread().getName() + "][BEFORE]");
        intFlux.subscribe(data -> System.out.println("[Thread Name = " + Thread.currentThread().getName() + "] data = " + data));
        sleep(5000);
        System.out.println("[Thread Name = " + Thread.currentThread().getName() + "][AFTER]");
    }
}
