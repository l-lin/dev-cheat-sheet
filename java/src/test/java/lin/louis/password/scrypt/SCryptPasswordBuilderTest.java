package lin.louis.password.scrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.password.PasswordBuilder;
import lin.louis.password.SaltGenerator;


class SCryptPasswordBuilderTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SCryptPasswordBuilderTest.class);

	private static final char[] PASSWORD_VALUE = "foobar".toCharArray();

	private final PasswordBuilder passwordBuilder = new SCryptPasswordBuilder();

	@Test
	void testBuild() {
		var salt = SaltGenerator.INSTANCE.get();
		var password = passwordBuilder.build(PASSWORD_VALUE, salt);
		LOGGER.info("password value: {}", new String(password.getValue()));

		var passwordWithSameSalt = passwordBuilder.build(PASSWORD_VALUE, salt);
		LOGGER.info("password with same salt value: {}", new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(new String(password.getValue()), new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(password, passwordWithSameSalt);

		var passwordWithDifferentSalt = passwordBuilder.build(PASSWORD_VALUE, SaltGenerator.INSTANCE.get());
		LOGGER.info("password with different salt value: {}", new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(new String(password.getValue()), new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(password, passwordWithDifferentSalt);
	}
}
