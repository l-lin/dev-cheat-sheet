package lin.louis.password;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class PasswordFactoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordFactoryTest.class);

	private static final char[] PASSWORD_VALUE = "foobar".toCharArray();

	@Test
	void testCreate() {
		var plain = PasswordFactory.createPlain(PASSWORD_VALUE);
		LOGGER.info("plain: {}", new String(plain.getValue()));
		var sha256 = PasswordFactory.createSha256(PASSWORD_VALUE);
		LOGGER.info("sha256: {}", new String(sha256.getValue()));
		var scrypt = PasswordFactory.createSCrypt(PASSWORD_VALUE);
		LOGGER.info("scrypt: {}", new String(scrypt.getValue()));
	}
}
