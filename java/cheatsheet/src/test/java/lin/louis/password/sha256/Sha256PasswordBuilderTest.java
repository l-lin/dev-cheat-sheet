package lin.louis.password.sha256;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.password.PasswordBuilder;
import lin.louis.password.SaltGenerator;


class Sha256PasswordBuilderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sha256PasswordBuilderTest.class);

	public static final char[] PASSWORD_VALUE = "foobar".toCharArray();

	private final PasswordBuilder passwordBuilder = new Sha256PasswordBuilder();

	@Test
	void testBuild() {
		var salt = SaltGenerator.INSTANCE.get();
		var password = passwordBuilder.build(PASSWORD_VALUE, salt);
		LOGGER.info("password hash: {}", new String(password.getValue()));

		var passwordWithSameSalt = passwordBuilder.build(PASSWORD_VALUE, salt);
		LOGGER.info("password with same salt hash: {}", new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(new String(password.getValue()), new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(password, passwordWithSameSalt);

		var passwordWithDifferentSalt = passwordBuilder.build(PASSWORD_VALUE, SaltGenerator.INSTANCE.get());
		LOGGER.info("password with different salt hash: {}", new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(new String(password.getValue()), new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(password, passwordWithDifferentSalt);
	}
}
