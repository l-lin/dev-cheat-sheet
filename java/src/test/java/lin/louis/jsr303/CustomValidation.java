package lin.louis.jsr303;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Oodrive
 * @author llin
 * @created 10/06/14 18:19
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
@Documented
public @interface CustomValidation {
    String message() default "The value is not equal to foo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
