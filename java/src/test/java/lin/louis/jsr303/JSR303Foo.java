package lin.louis.jsr303;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JSR303Foo {

    @NotBlank(message = "The firstName should not be blank!")
    private String firstName;

    @Size(min = 1, max = 5, message = "The lastName should have 5 characters max!")
    private String lastName;

    @CustomValidation
    private String foo;
}
