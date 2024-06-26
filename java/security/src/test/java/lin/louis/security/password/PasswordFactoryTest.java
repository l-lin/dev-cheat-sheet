package lin.louis.security.password;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PasswordFactoryTest {

	private static final char[] PASSWORD_VALUE = "foobar".toCharArray();

	@Test
	void testCreate() {
		var plain = PasswordFactory.createPlain(PASSWORD_VALUE);
		log.info("plain: {}", new String(plain.getValue()));
		var sha256 = PasswordFactory.createSha256(PASSWORD_VALUE);
		log.info("sha256: {}", new String(sha256.getValue()));
		var scrypt = PasswordFactory.createSCrypt(PASSWORD_VALUE);
		log.info("scrypt: {}", new String(scrypt.getValue()));
	}
}
