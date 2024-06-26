package lin.louis.security.sign.signer;

import java.security.GeneralSecurityException;

import lin.louis.security.sign.HashAlgo;


public interface Signer {
	String sign(HashAlgo hashAlgo, String privateKey, String message) throws GeneralSecurityException;
}
