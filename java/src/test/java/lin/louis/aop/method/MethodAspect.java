package lin.louis.aop.method;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class MethodAspect {
    private static Logger logger = LoggerFactory.getLogger(MethodAspect.class);

    @Pointcut("execution(* lin.louis.aop.mock.FooService.foo(..))")
    public void methodToTrack() {
    }

    @Around("methodToTrack()")
    public Object track(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        for (Object arg : arguments) {
            logger.info("Arguments is: " + arg);
        }
        Method method = retrieveTargetMethodFrom(joinPoint);
        logger.info("Method is: " + method.getName());
        Object retVal = joinPoint.proceed();
        logger.info("Return value is: " + retVal);
        return retVal;
    }

    private Method retrieveTargetMethodFrom(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // Do not fetch the method of the interface
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                        method.getParameterTypes());
            } catch (final SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }
}
