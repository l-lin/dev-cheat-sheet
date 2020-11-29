package lin.louis.reactor.logging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import reactor.core.publisher.Signal;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Helper to add MDC.
 * <p>
 * See https://projectreactor.io/docs/core/release/reference/#faq.mdc
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReactiveMDCLogging {

    public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> logStatement) {
        return log(Signal::isOnNext, Signal::get, logStatement);
    }

    public static <T> Consumer<Signal<T>> logOnComplete(Consumer<T> logStatement) {
        return log(Signal::isOnComplete, Signal::get, logStatement);
    }

    public static <P, T extends Throwable> Consumer<Signal<P>> logOnError(Consumer<T> logStatement) {

        return log(Signal::isOnError, signal -> {
            @SuppressWarnings("unchecked")
            T t = (T) signal.getThrowable();
            return t;
        }, logStatement);
    }

    private static <T, R> Consumer<Signal<T>> log(
            Predicate<Signal<T>> predicate,
            Function<Signal<T>, R> mapper,
            Consumer<R> logStatement
    ) {
        return signal -> {
            if (!predicate.test(signal)) {
                return;
            }
            filterContext(signal)
                    .forEach(entry -> MDC.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
            try {
                logStatement.accept(mapper.apply(signal));
            } finally {
                filterContext(signal)
                        .forEach(entry -> MDC.remove(String.valueOf(entry.getKey())));
            }
        };
    }

    private static <T> Stream<Map.Entry<Object, Object>> filterContext(final Signal<T> signal) {
        return signal.getContextView()
                .stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .filter(entry -> entry.getKey() instanceof String && entry.getValue() instanceof String);
    }
}
