package lin.louis.jsr303;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JSR303Foo {
    @NotBlank(message = "The firstName should not be blank!")
    private String firstName;

    @Size(min = 1, max = 5, message = "The lastName should have 5 characters max!")
    private String lastName;

    @CustomValidation
    private String foo;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	@Override
	public String toString() {
		return "JSR303Foo{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", foo='" + foo + '\'' +
				'}';
	}
}
