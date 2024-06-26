package lin.louis.security.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;


class AESStreamEncryptor {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	private static final int EOF = -1;

	private final Cipher cipher;

	AESStreamEncryptor(Cipher cipher) {
		this.cipher = cipher;
	}

	void encrypt(InputStream in, OutputStream out) throws IOException {
		try (var cipherOut = new CipherOutputStream(out, cipher)) {
			var buffer = new byte[DEFAULT_BUFFER_SIZE];
			int n;
			while (EOF != (n = in.read(buffer))) {
				cipherOut.write(buffer, 0, n);
			}
		}
	}
}
