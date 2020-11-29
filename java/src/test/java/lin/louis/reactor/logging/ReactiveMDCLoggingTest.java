package lin.louis.reactor.logging;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import static lin.louis.reactor.logging.ReactiveMDCLogging.logOnComplete;
import static lin.louis.reactor.logging.ReactiveMDCLogging.logOnError;
import static lin.louis.reactor.logging.ReactiveMDCLogging.logOnNext;

@Slf4j
class ReactiveMDCLoggingTest {

    @Test
    void testReactiveMDC() {
        Flux<Integer> flux = Flux.concat(
                Flux.just(1, 2, 3, 4, 5)
                        // context is propagated
                        .doOnEach(logOnNext(i -> log.info("Log on next with value set to {}", i)))
                        .doOnEach(logOnComplete(ignored -> log.info("Log on complete"))),
                Flux.error(new IllegalArgumentException("some error")),
                Flux.just(6)
        )
                .doOnEach(logOnError(t -> log.error("Log on error: {}", t.getMessage(), t)))
                .contextWrite(Context.of("foo", "bar"));

        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyError(IllegalArgumentException.class);
    }
}