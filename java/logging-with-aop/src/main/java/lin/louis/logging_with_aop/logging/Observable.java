package lin.louis.logging_with_aop.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Consumer;

/**
 * Annotation to add on the method you wish to monitor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Observable {

    /**
     * Set to false if you do not want to add MDCs on your observed method.
     */
    boolean addMdc() default true;

    /**
     * Set to false if you do not want to add logs on your observed method.
     */
    boolean addLogs() default true;

    /**
     * The log level to use when logging.
     */
    LogLevel logLevel() default LogLevel.INFO;

    /**
     * The class of the action to perform when method is successful
     */
    Class<? extends Consumer<?>>[] doOnSuccessClass() default {};

    /**
     * The class of the action to perform when method failed
     */
    Class<? extends Consumer<Exception>>[] doOnFailureClass() default {};

    enum LogLevel {
        INFO, DEBUG, TRACE
    }
}
