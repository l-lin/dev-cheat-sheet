package lin.louis.jsr303;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
class JSR303ValidationTest {

    @Test
    void testValidate() {
        JSR303Foo foo = new JSR303Foo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<JSR303Foo>> constraintViolations = validator.validate(foo);

        assertFalse(constraintViolations.isEmpty());
        constraintViolations.forEach(constraint -> log.info(
                "{}.{} {}",
                constraint.getRootBeanClass().getSimpleName(),
                constraint.getPropertyPath(),
                constraint.getMessage()
        ));
    }
}
