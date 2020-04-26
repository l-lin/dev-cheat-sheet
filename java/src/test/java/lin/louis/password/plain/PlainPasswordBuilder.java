package lin.louis.password.plain;

import lin.louis.password.Password;
import lin.louis.password.PasswordBuilder;


public class PlainPasswordBuilder implements PasswordBuilder  {

	@Override
	public Password build(char[] passwordValue, byte[] salt) {
		return new Password(passwordValue, salt);
	}
}
