package lin.louis.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AESStreamEncryptorDecryptorTest {

	private static final String PASSPHRASE = "foobar-barfoo-fo";

	private static final String FILE_NAME = "file/foobar.txt";

	@Test
	void encrypt_decrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		var salt = Salt.random();

		// ENCRYPT
		var cipher = AESCipherBuilder.start()
				.withSalt(salt)
				.withPassPhrase(PASSPHRASE)
				.buildEncryptor();
		var encryptor = new AESStreamEncryptor(cipher);
		byte[] initialContent;
		byte[] encryptedContent;
		try (
				var in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
				var out = new ByteArrayOutputStream()
		) {
			in.mark(0);
			encryptor.encrypt(in, out);
			encryptedContent = out.toByteArray();

			in.reset();
			// Used to compare afterword
			initialContent = in.readAllBytes();
		}

		// DECRYPT
		cipher = AESCipherBuilder.start()
				.withSalt(salt)
				.withPassPhrase(PASSPHRASE)
				.buildDecryptor();
		var decryptor = new AESStreamDecryptor(cipher);
		byte[] decryptedContent;
		try (
				var in = new ByteArrayInputStream(encryptedContent);
				var out = new ByteArrayOutputStream()
		) {
			decryptor.decrypt(in, out);
			decryptedContent = out.toByteArray();
		}

		Assertions.assertEquals(new String(initialContent), new String(decryptedContent));
	}
}
