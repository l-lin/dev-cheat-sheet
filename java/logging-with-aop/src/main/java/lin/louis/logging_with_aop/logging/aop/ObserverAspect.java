package lin.louis.logging_with_aop.logging.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lin.louis.logging_with_aop.logging.MdcInputFactory;
import lin.louis.logging_with_aop.logging.Observable;
import lin.louis.logging_with_aop.logging.Observer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Aspect
@RequiredArgsConstructor
public class ObserverAspect {

    private final Observer observer;
    private final MdcInputFactory mdcInputFactory;

    @Around("@annotation(observable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, Observable observable) {
        return this.observer.monitor(
                proceedingJoinPoint.getTarget().getClass(),
                () -> proceed(proceedingJoinPoint),
                observable,
                this.mdcInputFactory.create(proceedingJoinPoint.getArgs())
        );
    }

    @SneakyThrows
    private Object proceed(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.proceed();
    }
}
