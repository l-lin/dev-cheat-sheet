package lin.louis.encryption;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


class Salt {
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	// From com.sun.crypto.provider.AESConstants.AES_BLOCK_SIZE
	private static final int AES_IV_LENGTH = 16;
	private final byte[] salt;

	Salt(byte[] salt) {this.salt = salt;}

	byte[] getSalt() {
		// Prevent salt modification
		return salt == null ? null : Arrays.copyOf(salt, salt.length);
	}

	String getBase64Salt() {
		return Base64.getEncoder().encodeToString(salt);
	}

	static Salt random() {
		var data = new byte[AES_IV_LENGTH];
		SECURE_RANDOM.nextBytes(data);
		return new Salt(data);
	}
}
