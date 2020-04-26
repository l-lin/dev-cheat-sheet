package lin.louis.password.scrypt;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.bouncycastle.crypto.generators.SCrypt;

import lin.louis.password.Password;
import lin.louis.password.PasswordBuilder;


public class SCryptPasswordBuilder implements PasswordBuilder {

	@Override
	public Password build(char[] passwordValue, byte[] salt) {
		var byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(passwordValue));
		int cost = 1 << byteBuffer.get();
		byte blockSize = byteBuffer.get();
		byte parallelization = byteBuffer.get();
		byte len = byteBuffer.get();
		byte[] value = SCrypt.generate(byteBuffer.array(), salt, cost, blockSize, parallelization, len);
		return new Password(Base64.getEncoder().encodeToString(value).toCharArray(), salt);
	}
}
