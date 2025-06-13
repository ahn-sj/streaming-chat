## Netty와 이벤트 루프 패턴

- 대부분의 비동기 웹서버들이 이벤트 루프를 사용하여 비동기로 동작됨
  - NIO(Non-blocking I/O) Tomcat, Netty 등
- 이벤트 루프
  - 스레드가 루프를 계속 돌면서 이벤트를 처리하는 방식
- 이벤트 루프 스레드
  - 루프를 도는 스레드를 이벤트 루프 스레드라고 함

### 요청 흐름

1. 클라이언트가 요청을 보냄
2. 네티의 이벤트 루프 스레드가 요청을 받음
3. Accept 이벤트가 발생하여 요청을 처리할 준비를 함
4. EventLoop가 요청을 읽고, ClientRead 이벤트가 발생하여 요청 데이터를 읽음
5. 반환되는 Flux, Mono를 이벤트 루프 스레드가 구독함
6. 비즈니스 로직을 처리하고, 결과를 반환함
7. 클라이언트에게 데이터가 방출될때마다 ClientWrite 이벤트가 발생하여 데이터를 클라이언트에게 전송함

- ChannelSendOperator
- ByteBuffer
- AbstractChannelHandlerContext
  - writeAndFlush


## Subscriber - Publisher Pattern

### Flux로 어떻게 블로킹을 회피할 수 있는가?

- 스레드가 하나일 때는 블로킹이 발생할 수 있음. 따라서 블로킹을 피하기 위해서는 스레드를 늘려야 함.

```java
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
```
```java
// 실행결과
[Thread Name = Test worker][BEFORE]
[Thread Name = boundedElastic-1] data = 1
[Thread Name = boundedElastic-1] data = 2
[Thread Name = boundedElastic-1] data = 3
[Thread Name = boundedElastic-1] data = 4
[Thread Name = boundedElastic-1] data = 5
[Thread Name = boundedElastic-1] data = 6
[Thread Name = boundedElastic-1] data = 7
[Thread Name = boundedElastic-1] data = 8
[Thread Name = boundedElastic-1] data = 9
[Thread Name = Test worker][AFTER]
```

> **중요**<br/>
> 이벤트 루프 스레드의 블록킹을 막는 것이 핵심
> 
> 외부 API 호출 - WebClient
> DB 작업 - R2DBC
> 마땅히 I/O 작업이 아닌 경우 - 스케줄러(boundedElastic)
>   - 중요한 작업시 발생하는 모든 작업 -> 스레드 분리하여 JPA 활용
>   - 실시간으로 저장할 필요는 없는 데이터 -> MQ 활용
>   - 신뢰성이 떨어져도 되는 조회 -> R2DBC
 
> **WebFlux 블럭킹 처리 원칙**<br/>
> - R2DBC, WebClient 등 비동기 I/O를 지원하는 라이브러리를 사용하여 블로킹을 최소화
> - 블로킹이 필요한 경우, 병렬처리, 오래 걸리는 작업은 스케줄러를 사용하여 별도의 스레드에서 처리
> - 리액티브 프로그래밍의 기본 원칙은 스레드 변경 최소화이기 때문에 불필요한 스레드 분리는 삼가해야 함
