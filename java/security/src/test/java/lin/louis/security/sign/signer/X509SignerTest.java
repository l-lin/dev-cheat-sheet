package lin.louis.security.sign.signer;

import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.security.sign.HashAlgo;
import lin.louis.security.sign.TestFactories;
import lin.louis.security.sign.X509Certificates;
import lin.louis.security.sign.validator.SignValidator;
import lin.louis.security.sign.validator.X509SignValidator;


class X509SignerTest {
	private Signer signer = new X509Signer(TestFactories.signatureSupplierFactory());
	private SignValidator signValidator = new X509SignValidator(TestFactories.signatureSupplierFactory());

	@Test
	void sign() throws GeneralSecurityException {
		var message = "foobar";
		var result = signer.sign(HashAlgo.SHA256, X509Certificates.PRIVATE_KEY, message);

		Assertions.assertNotNull(result);
		Assertions.assertTrue(signValidator.isValid(HashAlgo.SHA256, X509Certificates.PUBLIC_KEY, message, result));
	}
}
