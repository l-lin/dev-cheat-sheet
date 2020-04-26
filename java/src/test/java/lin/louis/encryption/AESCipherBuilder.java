package lin.louis.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


class AESCipherBuilder {

	static SaltStep start() {
		return new Steps();
	}

	interface BuildStep {

		Cipher buildEncryptor()
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
				InvalidKeyException;

		Cipher buildDecryptor()
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
				InvalidKeyException;
	}

	interface SecretKeyStep {

		BuildStep withPassPhrase(String passPhrase);

		BuildStep withSecretKey(SecretKey secretKey);
	}

	interface SaltStep {

		SecretKeyStep withSalt(Salt salt);

		SecretKeyStep noSalt();
	}

	static class Steps implements SaltStep, SecretKeyStep, BuildStep {

		private static final String ALGO = "AES";

		private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

		private static final int[] AES_KEYSIZES = { 16, 24, 32 };

		private SecretKey secretKey;

		private Optional<IvParameterSpec> ivParameterSpec = Optional.empty();

		@Override
		public Cipher buildEncryptor()
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
				InvalidKeyException {
			return build(Cipher.ENCRYPT_MODE);
		}

		@Override
		public Cipher buildDecryptor()
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
				InvalidKeyException {
			return build(Cipher.DECRYPT_MODE);
		}

		@Override
		public BuildStep withPassPhrase(String secret) {
			if (!isKeySizeValid(secret.length())) {
				throw new IllegalArgumentException(String.format("Secret must have a conform length: %s. Got %d",
						Arrays.stream(AES_KEYSIZES).mapToObj(Integer::toString).collect(Collectors.joining(",")),
						secret.length()));
			}
			this.secretKey = new SecretKeySpec(secret.getBytes(), ALGO);
			return this;
		}

		@Override
		public BuildStep withSecretKey(SecretKey secretKey) {
			// Can be generated using:
			// KeyGenerator.getInstance("AES").generateKey();
			this.secretKey = secretKey;
			return this;
		}

		@Override
		public SecretKeyStep withSalt(Salt salt) {
			this.ivParameterSpec = Optional.of(new IvParameterSpec(salt.getSalt()));
			return this;
		}

		@Override
		public SecretKeyStep noSalt() {
			return this;
		}

		private Cipher build(int mode)
				throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
				InvalidKeyException {
			var cipher = Cipher.getInstance(TRANSFORMATION);
			if (ivParameterSpec.isPresent()) {
				cipher.init(mode, secretKey, ivParameterSpec.get());
			} else {
				cipher.init(mode, secretKey);
			}
			return cipher;
		}

		static boolean isKeySizeValid(int len) {
			for (int aesKeysize : AES_KEYSIZES) {
				if (len == aesKeysize) {
					return true;
				}
			}
			return false;
		}
	}
}
