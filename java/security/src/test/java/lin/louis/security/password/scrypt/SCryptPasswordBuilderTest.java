package lin.louis.security.password.scrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.security.password.PasswordBuilder;
import lin.louis.security.password.SaltGenerator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
class SCryptPasswordBuilderTest {

	private static final char[] PASSWORD_VALUE = "foobar".toCharArray();

	private final PasswordBuilder passwordBuilder = new SCryptPasswordBuilder();

	@Test
	void testBuild() {
		var salt = SaltGenerator.INSTANCE.get();
		var password = passwordBuilder.build(PASSWORD_VALUE, salt);
		log.info("password value: {}", new String(password.getValue()));

		var passwordWithSameSalt = passwordBuilder.build(PASSWORD_VALUE, salt);
		log.info("password with same salt value: {}", new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(new String(password.getValue()), new String(passwordWithSameSalt.getValue()));
		Assertions.assertEquals(password, passwordWithSameSalt);

		var passwordWithDifferentSalt = passwordBuilder.build(PASSWORD_VALUE, SaltGenerator.INSTANCE.get());
		log.info("password with different salt value: {}", new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(new String(password.getValue()), new String(passwordWithDifferentSalt.getValue()));
		Assertions.assertNotEquals(password, passwordWithDifferentSalt);
	}
}
