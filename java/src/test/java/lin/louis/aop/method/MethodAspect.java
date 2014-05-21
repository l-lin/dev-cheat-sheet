package lin.louis.aop.method;

import java.lang.reflect.Method;

import com.google.common.base.Throwables;
import lin.louis.aop.annotation.AnnotationOnMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Oodrive
 * @author llin
 * @created 21/05/14 12:40
 */
@Aspect
@Order(1)
@Component
public class MethodAspect {
    private static Logger logger = LoggerFactory.getLogger(MethodAspect.class);

    /**
     * Method to track.
     */
    @Pointcut("execution(* lin.louis.aop.mock.FooService.foo(..))")
    public void methodToTrack() {}

    /**
     * Track object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
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

    /**
     * Retrieve the IMPLEMENTATION method from the joinpoint.
     *
     * @param joinPoint the join point
     * @return the method
     * @throws Throwable the throwable
     */
    private Method retrieveTargetMethodFrom(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = checkNotNull(signature.getMethod(), "Could not find the tracked method!");
        // Do not fetch the method of the interface
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                        method.getParameterTypes());
            } catch (final SecurityException | NoSuchMethodException e) {
                throw Throwables.propagate(e);
            }
        }
        return method;
    }
}
