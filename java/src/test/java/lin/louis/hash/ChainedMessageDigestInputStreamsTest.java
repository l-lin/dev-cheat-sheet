package lin.louis.hash;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;


class ChainedMessageDigestInputStreamsTest {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	private static final int EOF = -1;

	@Test
	void read() throws IOException {
		try (
				var in = this.getClass().getClassLoader().getResourceAsStream("file/foo.txt");
				var out = new ByteArrayOutputStream();
				var chainedIn = new ChainedMessageDigestInputStreams(buildMessageDigestMap(), in)
		) {
			var buffer = new byte[DEFAULT_BUFFER_SIZE];
			int n;
			while (EOF != (n = chainedIn.read(buffer))) {
				out.write(buffer, 0, n);
			}

			var hashes = chainedIn.computeHashes();
			assertAll(
					() -> {
						assertNotNull(hashes);
						assertEquals(3, hashes.size());
						assertTrue(hashes.containsKey("SUN:MD5"));
						assertTrue(hashes.containsKey("SUN:SHA-1"));
						assertTrue(hashes.containsKey("SUN:SHA-256"));
						var encoder = Base64.getEncoder();
						assertEquals("OFj2IjCsPJFfMAxmQxLGPw==", encoder.encodeToString(hashes.get("SUN:MD5")));
						assertEquals("iEPX+SQWIR3p67lj/0zigSWTKHg=", encoder.encodeToString(hashes.get("SUN:SHA-1")));
						assertEquals("w6uP8Tcg6K2QR905Rms8iXTlksL6OD1KOWBxTK7wxPI=",
								encoder.encodeToString(hashes.get("SUN:SHA-256")));
					},
					() -> {
						var content = new String(out.toByteArray());
						assertEquals("foobar", content);
					});
		}
	}

	private Map<String, MessageDigest> buildMessageDigestMap() {
		var m = new HashMap<String, MessageDigest>();
		m.put("SUN:MD5", getMessageDigest("MD5", "SUN"));
		m.put("SUN:SHA-1", getMessageDigest("SHA-1", "SUN"));
		m.put("SUN:SHA-256", getMessageDigest("SHA-256", "SUN"));
		return m;
	}

	private static MessageDigest getMessageDigest(String algorithm, String provider) {
		try {
			return MessageDigest.getInstance(algorithm, provider);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
