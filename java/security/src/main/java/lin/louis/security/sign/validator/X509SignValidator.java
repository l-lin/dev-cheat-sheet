package lin.louis.security.sign.validator;

import java.io.ByteArrayInputStream;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import lin.louis.security.sign.HashAlgo;
import lin.louis.security.sign.supplier.SignatureSupplierFactory;


public class X509SignValidator implements SignValidator {

	private static CertificateFactory certificateFactory;

	private final SignatureSupplierFactory signatureSupplierFactory;

	static {
		try {
			certificateFactory = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e) {
			throw new RuntimeException(e);
		}
	}

	public X509SignValidator(SignatureSupplierFactory signatureSupplierFactory) {
		this.signatureSupplierFactory = signatureSupplierFactory;
	}

	@Override
	public boolean isValid(HashAlgo hashAlgo, String publicKey, String message, String signatureStr)
			throws GeneralSecurityException {
		var publicKeyBytes = Base64.getDecoder().decode(publicKey);
		X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(
				new ByteArrayInputStream(publicKeyBytes));

		var signatureBytes = Base64.getDecoder().decode(signatureStr);
		var data = message.getBytes();

		Signature signature = signatureSupplierFactory.create(
				hashAlgo,
				certificate.getPublicKey());

		signature.initVerify(certificate);
		signature.update(data);
		return signature.verify(signatureBytes);
	}
}
