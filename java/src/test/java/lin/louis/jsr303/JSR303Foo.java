package lin.louis.jsr303;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author llin
 * @created 10/06/14 18:25
 */
@Data
@ToString
public class JSR303Foo {
    @NotBlank(message = "The firstName should not be blank!")
    private String firstName;

    @Size(min = 1, max = 5, message = "The lastName should have 5 characters max!")
    private String lastName;

    @CustomValidation
    private String foo;
}
