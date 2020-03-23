package lin.louis.password.sha256;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.password.Password;
import lin.louis.password.PasswordBuilder;


public class Sha256PasswordBuilder implements PasswordBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sha256PasswordBuilder.class);

	private static final MessageDigest DIGEST;

	static {
		try {
			DIGEST = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
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
