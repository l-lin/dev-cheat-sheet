package lin.louis.security.password.plain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.security.password.PasswordBuilder;
import lin.louis.security.password.SaltGenerator;


class PlainPasswordBuilderTest {

	private static final String PASSWORD_VALUE = "foobar";

	private final PasswordBuilder passwordBuilder = new PlainPasswordBuilder();

	@Test
	void testBuild() {
		var password = passwordBuilder.build(PASSWORD_VALUE.toCharArray(), SaltGenerator.INSTANCE.get());
		Assertions.assertEquals(PASSWORD_VALUE, new String(password.getValue()));
	}
}
