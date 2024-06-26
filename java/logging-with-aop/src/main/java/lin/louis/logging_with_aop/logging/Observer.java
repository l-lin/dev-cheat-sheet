package lin.louis.logging_with_aop.logging;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.function.Predicate.not;

/**
 * Add logs and perform action on success/failure for methods annotated with {@link Observable}.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Observer {

    private final ActionsFactory actionsFactory;
    private final MdcHandler mdcHandler;
    private final Function<Class<?>, Logger> loggerProvider;

    public static Observer create(ActionsFactory actionsFactory) {
        return new Observer(
                actionsFactory,
                new MdcHandler(),
                clazz -> LoggerFactory.getLogger(clazz.getCanonicalName())
        );
    }

    public <O> O monitor(
            Class<?> monitoredClass,
            Supplier<O> monitoredMethod,
            Observable observable,
            List<MdcInput> mdcInputs
    ) {
        List<MdcInput> mdcInputToSet = mdcInputs.stream().filter(not(this.mdcHandler::isMdcAlreadySet)).toList();

        if (observable.addMdc()) {
            mdcInputToSet.forEach(this.mdcHandler::put);
        }

        Logger logger = this.loggerProvider.apply(monitoredClass);
        try {
            log(observable, monitoredClass, logger, "START {}");

            O result = monitoredMethod.get();

            log(observable, monitoredClass, logger, "SUCCESS {}");

            doOnSuccess(observable, result);

            return result;
        } catch (Exception e) {
            logger.error("FAILURE {}", monitoredClass.getSimpleName());

            doOnFailure(observable, e);
            throw e;
        } finally {
            if (observable.addMdc()) {
                mdcInputToSet.forEach(this.mdcHandler::remove);
            }
        }
    }

    private void log(Observable observable, Class<?> monitoredClass, Logger logger, String message) {
        if (observable.addLogs()) {
            switch (observable.logLevel()) {
                case INFO -> logger.info(message, monitoredClass.getSimpleName());
                case DEBUG -> logger.debug(message, monitoredClass.getSimpleName());
                case TRACE -> logger.trace(message, monitoredClass.getSimpleName());
            }
        }
    }

    private <O> void doOnSuccess(Observable observable, O result) {
        if (observable.doOnSuccessClass() != null) {
            Arrays.stream(observable.doOnSuccessClass())
                    .map(this.actionsFactory::create)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(consumer -> (Consumer<O>) consumer)
                    .forEach(action -> executeActionSafely(action, result));
        }
    }

    private void doOnFailure(Observable observable, Exception e) {
        if (observable.doOnFailureClass() != null) {
            Arrays.stream(observable.doOnFailureClass())
                    .map(this.actionsFactory::create)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(action -> executeActionSafely(action, e));
        }
    }

    private static <I> void executeActionSafely(Consumer<I> action, I result) {
        try {
            action.accept(result);
        } catch (Exception e) {
            // we should not propagate the exception to the observed method
            log.error(
                    "Action {} was not executed successfully: {}",
                    action.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );
        }
    }
}
