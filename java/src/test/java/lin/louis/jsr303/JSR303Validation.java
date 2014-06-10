package lin.louis.jsr303;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import java.util.Set;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 10/06/14 18:04
 */
public class JSR303Validation {
    @Test
    public void testValidate() {
        Foo foo = new Foo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Foo>> constraintViolations = validator.validate(foo);

        assertThat(constraintViolations.size()).isGreaterThan(0);

        System.out.println("\n---------------------------------------------------------");
        for (ConstraintViolation<Foo> contraintes : constraintViolations) {
            System.out.println(contraintes.getRootBeanClass().getSimpleName() + "." + contraintes.getPropertyPath() + " " + contraintes.getMessage());
        }
        System.out.println("---------------------------------------------------------\n");
    }

    @Data
    @ToString
    private class Foo {
        @NotBlank(message = "The firstName should not be blank!")
        private String firstName;

        @Size(min = 1, max = 5, message = "The lastName should have 5 characters max!")
        private String lastName;

        @CustomValidation
        private String foo;
    }
}
