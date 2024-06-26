package lin.louis.security.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;


class AESStreamDecryptor {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	private static final int EOF = -1;

	private final Cipher cipher;

	AESStreamDecryptor(Cipher cipher) {
		this.cipher = cipher;
	}

	void decrypt(InputStream in, OutputStream out)
			throws IOException {
		try (var cipherIn = new CipherInputStream(in, cipher)) {
			var buffer = new byte[DEFAULT_BUFFER_SIZE];
			int n;
			while (EOF != (n = cipherIn.read(buffer))) {
				out.write(buffer, 0, n);
			}
		}
	}
}
