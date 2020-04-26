package lin.louis.sign.validator;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

import lin.louis.sign.HashAlgo;


public interface SignValidator {

	boolean isValid(HashAlgo hashAlgo, String publicKey, String message, String signatureStr)
			throws GeneralSecurityException;
}
