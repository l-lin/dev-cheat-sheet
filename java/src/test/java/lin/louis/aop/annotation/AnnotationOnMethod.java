package lin.louis.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author llin
 * @created 20/05/14 09:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface AnnotationOnMethod {
    /**
     * Value string.
     *
     * @return the string
     */
    String value();
}
