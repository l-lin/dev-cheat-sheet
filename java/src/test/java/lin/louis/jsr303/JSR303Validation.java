package lin.louis.jsr303;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 10/06/14 18:04
 */
public class JSR303Validation {
    @Test
    public void testValidate() {
        JSR303Foo foo = new JSR303Foo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<JSR303Foo>> constraintViolations = validator.validate(foo);

        assertThat(constraintViolations.size()).isGreaterThan(0);

        System.out.println("\n---------------------------------------------------------");
        for (ConstraintViolation<JSR303Foo> contraintes : constraintViolations) {
            System.out.println(contraintes.getRootBeanClass().getSimpleName() + "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
        }
        System.out.println("---------------------------------------------------------\n");
    }
}
