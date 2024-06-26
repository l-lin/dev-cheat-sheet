package lin.louis.jsr303;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class JSR303ValidationTest {

    @Test
    void testValidate() {
        JSR303Foo foo = new JSR303Foo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<JSR303Foo>> constraintViolations = validator.validate(foo);

        assertFalse(constraintViolations.isEmpty());
        constraintViolations.forEach(constraint -> System.out.println(
                "%s.%s %s".formatted(
                constraint.getRootBeanClass().getSimpleName(),
                constraint.getPropertyPath(),
                constraint.getMessage()
        )));
    }
}
