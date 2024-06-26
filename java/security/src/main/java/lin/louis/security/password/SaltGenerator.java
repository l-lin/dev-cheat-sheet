package lin.louis.security.password;

import java.security.SecureRandom;
import java.util.function.Supplier;


public enum SaltGenerator implements Supplier<byte[]> {
	INSTANCE;

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int SALT_LENGTH = 16;

	@Override
	public byte[] get() {
		var salt = new byte[SALT_LENGTH];
		RANDOM.nextBytes(salt);
		return salt;
	}
}
