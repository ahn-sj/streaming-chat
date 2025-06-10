package tally.chatting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FunctionalProgrammingTest {

    @Test
    public void produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            sink.add(i);
        }

        sink = map(sink);
        sink = filter(sink);
        forEach(sink);
    }

    private void forEach(final List<Integer> sink) {
        for (int i = 0; i < sink.size(); i++) {
            System.out.println(sink.get(i));
        }
    }

    private List<Integer> filter(List<Integer> sink) {
        List<Integer> newSink2 = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            if( sink.get(i) % 4 == 0) {
                newSink2.add(sink.get(i));
            }
        }
        sink = newSink2;
        return sink;
    }

    private List<Integer> map(List<Integer> sink) {
        List<Integer> newSink1 = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            newSink1.add(sink.get(i) * 2);
        }
        sink = newSink1;
        return sink;
    }
}
