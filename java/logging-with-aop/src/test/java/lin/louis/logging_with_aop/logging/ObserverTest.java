package lin.louis.logging_with_aop.logging;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;

class ObserverTest {

    private ActionsFactory actionsFactory;
    private MdcHandler mdcHandler;
    private Logger logger;

    private Observer observer;

    @BeforeEach
    void beforeEach() {
        this.actionsFactory = mock(ActionsFactory.class);
        this.mdcHandler = mock(MdcHandler.class);
        this.logger = mock(Logger.class);

        this.observer = new Observer(this.actionsFactory, this.mdcHandler, ignored -> this.logger);
    }

    @ParameterizedTest
    @EnumSource(value = Observable.LogLevel.class)
    @DisplayName("""
            Given observable with MDC and no action,
             when monitoring the class and its method,
             then add MDC, log and do not perform any action on success or failure
            """)
    void observableWithMdcAndLogsAndNoAction(Observable.LogLevel logLevel) throws NoSuchMethodException {
        // GIVEN
        TestClass testClass = new TestClass();
        Method method = testClass.getClass()
                .getMethod("observableWithMdcAndLogsAndNoActionAndLogLevel" + logLevel.name());
        Observable observable = method.getAnnotation(Observable.class);

        MdcInput mdcInput = new MdcInput("foo", "bar");
        MdcInput mdcInputShouldNotBeSet = new MdcInput("shouldNotBeSet_key", "shouldNotBeSet_value");
        given(this.mdcHandler.isMdcAlreadySet(mdcInputShouldNotBeSet)).willReturn(true);

        // WHEN
        Object result = this.observer.monitor(
                testClass.getClass(),
                () -> {
                    try {
                        return method.invoke(testClass);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                },
                observable,
                List.of(mdcInput, mdcInputShouldNotBeSet)
        );

        // THEN
        assertTrue(result instanceof Integer);
        assertEquals(1, result);

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);
        then(this.mdcHandler).should(never()).put(mdcInputShouldNotBeSet);
        then(this.mdcHandler).should(never()).remove(mdcInputShouldNotBeSet);

        switch (logLevel) {
            case TRACE -> {
                then(this.logger).should().trace("START {}", TestClass.class.getSimpleName());
                then(this.logger).should().trace("SUCCESS {}", TestClass.class.getSimpleName());
                then(this.logger).should(never()).debug(any(String.class), any(Class.class));
                then(this.logger).should(never()).info(any(String.class), any(Class.class));
            }
            case DEBUG -> {
                then(this.logger).should(never()).trace(any(String.class), any(Class.class));
                then(this.logger).should().debug("START {}", TestClass.class.getSimpleName());
                then(this.logger).should().debug("SUCCESS {}", TestClass.class.getSimpleName());
                then(this.logger).should(never()).info(any(String.class), any(Class.class));
            }
            case INFO -> {
                then(this.logger).should(never()).trace(any(String.class), any(Class.class));
                then(this.logger).should(never()).debug(any(String.class), any(Class.class));
                then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
                then(this.logger).should().info("SUCCESS {}", TestClass.class.getSimpleName());
            }
        }

        then(this.actionsFactory).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("""
            Given observable with no MDC and no log and no action,
             when monitoring the class and its method,
             then add MDC, log and do not perform any action on success or failure
            """)
    void observableWithNoMdcAndNoLogAndNoAction() throws NoSuchMethodException {
        // GIVEN
        TestClass testClass = new TestClass();
        Method method = testClass.getClass().getMethod("observableWithoutMdcAndNoLogAndNoAction");
        Observable observable = method.getAnnotation(Observable.class);

        MdcInput mdcInputShouldNotBeSet = new MdcInput("shouldNotBeSet_key", "shouldNotBeSet_value");

        // WHEN
        int result = this.observer.monitor(
                testClass.getClass(),
                testClass::observableWithoutMdcAndNoLogAndNoAction,
                observable,
                List.of(mdcInputShouldNotBeSet)
        );

        // THEN
        assertEquals(2, result);

        then(this.mdcHandler).should(never()).put(any(MdcInput.class));
        then(this.mdcHandler).should(never()).remove(any(MdcInput.class));

        then(this.logger).shouldHaveNoInteractions();
    }

    // doOnSuccessAction

    @Test
    @DisplayName("""
            Given observable with on success action,
             when monitoring the class and its method,
             then add MDC, log and perform action on success
            """)
    void observableWithOnSuccessAction() throws NoSuchMethodException {
        // GIVEN
        TestClass testClass = new TestClass();
        Method method = testClass.getClass().getMethod("observableWithOnSuccessAction");
        Observable observable = method.getAnnotation(Observable.class);

        MdcInput mdcInput = new MdcInput("foo", "bar");

        DoOnSuccessAction doOnSuccessAction = mock(DoOnSuccessAction.class);
        given(this.actionsFactory.create(DoOnSuccessAction.class))
                .willReturn(Optional.of(doOnSuccessAction));

        // WHEN
        int result = this.observer.monitor(
                testClass.getClass(),
                testClass::observableWithOnSuccessAction,
                observable,
                List.of(mdcInput)
        );

        // THEN
        assertEquals(3, result);

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should().info("SUCCESS {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnSuccessAction.class);
        then(doOnSuccessAction).should(only()).accept(3);
    }

    @Test
    @DisplayName("""
            Given observable with on success action that throws an exception,
             when monitoring the class and its method,
             then add MDC, log, perform action on success
             and do not propagate the exception
            """)
    void observableWithOnSuccessActionThatThrowsAnException() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();
        Method method = testClass.getClass().getMethod("observableWithOnSuccessAction");
        Observable observable = method.getAnnotation(Observable.class);

        var mdcInput = new MdcInput("foo", "bar");

        DoOnSuccessAction doOnSuccessAction = mock(DoOnSuccessAction.class);
        willThrow(new IllegalStateException("error")).given(doOnSuccessAction).accept(3);
        given(this.actionsFactory.create(DoOnSuccessAction.class))
                .willReturn(Optional.of(doOnSuccessAction));

        // WHEN
        int result = assertDoesNotThrow(() -> this.observer.monitor(
                testClass.getClass(),
                testClass::observableWithOnSuccessAction,
                observable,
                List.of(mdcInput)
        ));

        // THEN
        assertEquals(3, result);

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should().info("SUCCESS {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnSuccessAction.class);
        then(doOnSuccessAction).should(only()).accept(3);
    }

    @Test
    @DisplayName("""
            Given observable with on success action and unknown action class,
             when monitoring the class and its method,
             then add MDC, log and perform action on success
            """)
    void observableWithOnSuccessAction_and_unknownActionClass() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();
        Method method = testClass.getClass().getMethod("observableWithOnSuccessAction");
        Observable observable = method.getAnnotation(Observable.class);

        var mdcInput = new MdcInput("foo", "bar");

        given(this.actionsFactory.create(DoOnSuccessAction.class)).willReturn(Optional.empty());

        // WHEN
        int result = this.observer.monitor(
                testClass.getClass(),
                testClass::observableWithOnSuccessAction,
                observable,
                List.of(mdcInput)
        );

        // THEN
        assertEquals(3, result);

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should().info("SUCCESS {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnSuccessAction.class);
    }

    // doOnFailure

    @Test
    @DisplayName("""
            Given observable with on failure action,
             when monitoring the class and its method,
             then add MDC, log and perform action on failure
            """)
    void observableWithOnFailureAction() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();
        Class<? extends TestClass> clazz = testClass.getClass();
        Method method = clazz.getMethod("observableWithOnFailureAction");
        var observable = method.getAnnotation(Observable.class);

        var mdcInput = new MdcInput("foo", "bar");
        var mdcInputs = List.of(mdcInput);

        DoOnFailureAction doOnFailureAction = mock(DoOnFailureAction.class);
        given(this.actionsFactory.create(DoOnFailureAction.class))
                .willReturn(Optional.of(doOnFailureAction));

        // WHEN / THEN
        assertThrows(IllegalStateException.class, () -> this.observer.monitor(
                clazz,
                testClass::observableWithOnFailureAction,
                observable,
                mdcInputs
        ));

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should(never()).info("SUCCESS {}", TestClass.class.getSimpleName());
        then(this.logger).should().error("FAILURE {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnFailureAction.class);
        then(doOnFailureAction).should(only()).accept(any(IllegalStateException.class));
    }

    @Test
    @DisplayName("""
            Given observable with on failure action that throws an exception,
             when monitoring the class and its method,
             then add MDC, log, perform action on failure
             and do not propagate the execpetion
            """)
    void observableWithOnFailureActionThatThrowsAnException() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();
        Class<? extends TestClass> clazz = testClass.getClass();
        Method method = clazz.getMethod("observableWithOnFailureAction");
        Observable observable = method.getAnnotation(Observable.class);

        var mdcInput = new MdcInput("foo", "bar");
        var mdcInputs = List.of(mdcInput);

        DoOnFailureAction doOnFailureAction = mock(DoOnFailureAction.class);
        willThrow(new IllegalArgumentException("error"))
                .given(doOnFailureAction).accept(any(IllegalStateException.class));
        given(this.actionsFactory.create(DoOnFailureAction.class))
                .willReturn(Optional.of(doOnFailureAction));

        // WHEN / THEN
        assertThrows(IllegalStateException.class, () -> this.observer.monitor(
                clazz,
                testClass::observableWithOnFailureAction,
                observable,
                mdcInputs
        ));

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should(never()).info("SUCCESS {}", TestClass.class.getSimpleName());
        then(this.logger).should().error("FAILURE {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnFailureAction.class);
        then(doOnFailureAction).should(only()).accept(any(IllegalStateException.class));
    }

    @Test
    @DisplayName("""
            Given observable with on failure action and unknown action class,
             when monitoring the class and its method,
             then add MDC, log and perform action on failure
            """)
    void observableWithOnFailureAction_and_unknownActionClass() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();
        Class<? extends TestClass> clazz = testClass.getClass();
        Method method = clazz.getMethod("observableWithOnFailureAction");
        Observable observable = method.getAnnotation(Observable.class);

        var mdcInput = new MdcInput("foo", "bar");
        var mdcInputs = List.of(mdcInput);

        given(this.actionsFactory.create(DoOnFailureAction.class)).willReturn(Optional.empty());

        // WHEN / THEN
        assertThrows(IllegalStateException.class, () -> this.observer.monitor(
                clazz,
                testClass::observableWithOnFailureAction,
                observable,
                mdcInputs
        ));

        then(this.mdcHandler).should().put(mdcInput);
        then(this.mdcHandler).should().remove(mdcInput);

        then(this.logger).should(never()).trace(any(String.class), any(Class.class));
        then(this.logger).should(never()).debug(any(String.class), any(Class.class));
        then(this.logger).should().info("START {}", TestClass.class.getSimpleName());
        then(this.logger).should(never()).info("SUCCESS {}", TestClass.class.getSimpleName());
        then(this.logger).should().error("FAILURE {}", TestClass.class.getSimpleName());

        then(this.actionsFactory).should(only()).create(DoOnFailureAction.class);
    }

    private static class TestClass {

        @Observable(logLevel = Observable.LogLevel.INFO)
        public int observableWithMdcAndLogsAndNoActionAndLogLevelINFO() {
            return 1;
        }

        @Observable(logLevel = Observable.LogLevel.DEBUG)
        public int observableWithMdcAndLogsAndNoActionAndLogLevelDEBUG() {
            return 1;
        }

        @Observable(logLevel = Observable.LogLevel.TRACE)
        public int observableWithMdcAndLogsAndNoActionAndLogLevelTRACE() {
            return 1;
        }

        @Observable(addMdc = false, addLogs = false)
        public int observableWithoutMdcAndNoLogAndNoAction() {
            return 2;
        }

        @Observable(doOnSuccessClass = DoOnSuccessAction.class)
        public int observableWithOnSuccessAction() {
            return 3;
        }

        @Observable(doOnFailureClass = DoOnFailureAction.class)
        public int observableWithOnFailureAction() {
            throw new IllegalStateException("foobar");
        }
    }

    private static class DoOnSuccessAction implements Consumer<Integer> {

        @Override
        public void accept(Integer integer) {}
    }

    private static class DoOnFailureAction implements Consumer<Exception> {

        @Override
        public void accept(Exception exception) {}
    }
}
