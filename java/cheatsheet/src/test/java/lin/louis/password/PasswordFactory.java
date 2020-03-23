package lin.louis.password;

import lin.louis.password.plain.PlainPasswordBuilder;
import lin.louis.password.scrypt.SCryptPasswordBuilder;
import lin.louis.password.sha256.Sha256PasswordBuilder;


class PasswordFactory {

	static Password createPlain(char[] passwordValue) {
		return create(new PlainPasswordBuilder(), passwordValue);
	}

	static Password createSha256(char[] passwordValue) {
		return create(new Sha256PasswordBuilder(), passwordValue);
	}

	static Password createSCrypt(char[] passwordValue) {
		return create(new SCryptPasswordBuilder(), passwordValue);
	}

	static Password create(PasswordBuilder builder, char[] passwordValue) {
		return builder.build(passwordValue, SaltGenerator.INSTANCE.get());
	}
}
