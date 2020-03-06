package lin.louis.sign.signer;

import java.security.GeneralSecurityException;

import lin.louis.sign.HashAlgo;


public interface Signer {
	String sign(HashAlgo hashAlgo, String privateKey, String message) throws GeneralSecurityException;
}
