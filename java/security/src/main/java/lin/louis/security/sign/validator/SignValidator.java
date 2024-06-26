package lin.louis.security.sign.validator;

import java.security.GeneralSecurityException;

import lin.louis.security.sign.HashAlgo;


public interface SignValidator {

	boolean isValid(HashAlgo hashAlgo, String publicKey, String message, String signatureStr)
			throws GeneralSecurityException;
}
