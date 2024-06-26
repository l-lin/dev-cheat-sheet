package lin.louis.security.password.plain;

import lin.louis.security.password.Password;
import lin.louis.security.password.PasswordBuilder;


public class PlainPasswordBuilder implements PasswordBuilder  {

	@Override
	public Password build(char[] passwordValue, byte[] salt) {
		return new Password(passwordValue, salt);
	}
}
