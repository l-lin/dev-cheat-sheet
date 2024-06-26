package lin.louis.security.password;

import java.util.Arrays;


public class Password {
	private final char[] value;
	private final byte[] salt;

	public Password(char[] value, byte[] salt) {
		this.value = value;
		this.salt = salt;
	}

	public byte[] getSalt() {
		return Arrays.copyOf(salt, salt.length);
	}

	public char[] getValue() {
		return Arrays.copyOf(value, value.length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Password password = (Password) o;
		return Arrays.equals(value, password.value) &&
				Arrays.equals(salt, password.salt);
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(value);
		result = 31 * result + Arrays.hashCode(salt);
		return result;
	}
}
