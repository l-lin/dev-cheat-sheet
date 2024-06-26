package lin.louis.security.encryption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SaltTest {

	@Test
	void salt() {
		var s = Salt.random();
		var saltStr = s.getBase64Salt();
		s.getSalt()[0] = 'c';
		Assertions.assertEquals(saltStr, s.getBase64Salt());
	}
}
