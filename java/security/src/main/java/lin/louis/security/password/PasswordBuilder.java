package lin.louis.security.password;

public interface PasswordBuilder {
	Password build(char[] passwordValue, byte[] salt);
}
