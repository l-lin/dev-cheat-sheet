package lin.louis.logging_with_aop.logging.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import lin.louis.logging_with_aop.logging.MdcInput;
import lin.louis.logging_with_aop.logging.MdcInputFactory;
import lin.louis.logging_with_aop.logging.Observable;
import lin.louis.logging_with_aop.logging.Observer;

class ObservableObserverAspectTest {

    private Observer observer;
    private MdcInputFactory mdcInputFactory;

    private ObserverAspect observerAspect;

    @BeforeEach
    void beforeEach() {
        this.observer = mock(Observer.class);
        this.mdcInputFactory = mock(MdcInputFactory.class);

        this.observerAspect = new ObserverAspect(this.observer, this.mdcInputFactory);
    }

    @Test
    @DisplayName("""
            Given a method with @Observable annotation,
             when calling the observed method,
             then the aspect is also called
            """)
    @SuppressWarnings("unchecked")
    void aMethodWithObservableAnnotation() throws NoSuchMethodException {
        // GIVEN
        var testClass = new TestClass();

        AspectJProxyFactory factory = new AspectJProxyFactory(testClass);
        factory.addAspect(this.observerAspect);
        TestClass testClassWithAspect = factory.getProxy();

        Method method = testClass.getClass().getMethod("add", int.class, int.class);
        Observable observable = method.getAnnotation(Observable.class);

        List<MdcInput> expectedMdcInputs = List.of(new MdcInput("1", "2"));
        given(this.mdcInputFactory.create(new Object[]{1, 2})).willReturn(expectedMdcInputs);

        var expectedOutput = 3;
        given(this.observer.monitor(
                eq(TestClass.class),
                any(Supplier.class),
                eq(observable),
                eq(expectedMdcInputs)
        )).willReturn(expectedOutput);

        // WHEN
        int actualOutput = testClassWithAspect.add(1, 2);

        // THEN
        assertEquals(expectedOutput, actualOutput);
        then(this.mdcInputFactory).should(only()).create(new Object[]{1, 2});
        then(this.observer).should(only()).monitor(
                eq(TestClass.class),
                any(Supplier.class),
                eq(observable),
                eq(expectedMdcInputs)
        );
    }

    static class TestClass {

        @Observable
        public int add(int a, int b) {
            return a + b;
        }
    }
}

