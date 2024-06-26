package lin.louis.security.password.sha256;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lin.louis.security.password.Password;
import lin.louis.security.password.PasswordBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sha256PasswordBuilder implements PasswordBuilder {

	private static final MessageDigest DIGEST;

	static {
		try {
			DIGEST = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Password build(char[] passwordValue, byte[] salt) {
		DIGEST.reset();
		DIGEST.update(salt);
		var byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(passwordValue));
		var hash = DIGEST.digest(byteBuffer.array());
		return new Password(Base64.getEncoder().encodeToString(hash).toCharArray(), salt);
	}
}
