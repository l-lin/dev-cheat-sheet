package lin.louis.sign.signer;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lin.louis.sign.HashAlgo;
import lin.louis.sign.supplier.SignatureSupplierFactory;


public class X509Signer implements Signer {

	private final SignatureSupplierFactory signatureSupplierFactory;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public X509Signer(SignatureSupplierFactory signatureSupplierFactory) {
		this.signatureSupplierFactory = signatureSupplierFactory;
	}

	@Override
	public String sign(HashAlgo hashAlgo, String privateKey, String message) throws GeneralSecurityException {
		var data = message.getBytes();
		var key = build(privateKey);
		var signature = signatureSupplierFactory.create(hashAlgo, key);
		signature.initSign(key);
		signature.update(data);
		return Base64.getEncoder().encodeToString(signature.sign());
	}

	private static PrivateKey build(String privateKeyStr) throws GeneralSecurityException {
		PKCS8EncodedKeySpec keySpecPv = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
		return KeyFactory.getInstance("RSA").generatePrivate(keySpecPv);
	}
}
