package lin.louis.password;

public interface PasswordBuilder {
	Password build(char[] passwordValue, byte[] salt);
}
