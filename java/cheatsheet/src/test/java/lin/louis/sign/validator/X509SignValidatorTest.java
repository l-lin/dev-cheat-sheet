package lin.louis.sign.validator;

import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.sign.HashAlgo;
import lin.louis.sign.TestFactories;
import lin.louis.sign.X509Certificates;


class X509SignValidatorTest {
	private final SignValidator signValidator = new X509SignValidator(TestFactories.signatureSupplierFactory());

	@Test
	void isValid() throws GeneralSecurityException {
		var isValid = signValidator.isValid(HashAlgo.SHA256, X509Certificates.PUBLIC_KEY, "foobar", "sdGDf03r+Ngzok8Lqz9BMPWWe1gnF6XoHQOzGanmIzZsyRHmwxg2fSu6dW05C8q9d3b8RanA+hBfom250vy0c2vklmd/2/HfrQN1BPghe88Ba05F394EaTTfptleddnz9AFQrXRyYoxotbKLkKuC0fNO/T2YS52+hO8dXtbpNB8=");

		Assertions.assertTrue(isValid);
	}
}
