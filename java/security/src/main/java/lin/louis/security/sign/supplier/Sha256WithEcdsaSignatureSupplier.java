package lin.louis.security.sign.supplier;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Security;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lin.louis.security.sign.HashAlgo;


public class Sha256WithEcdsaSignatureSupplier implements SignatureSupplier {

    private static final String ALGO_NAME_ECDSA = "SHA256withECDSA";
    private static final List<String> ACCEPTED_ALGORITHMS = Arrays.asList("ECDSA", "EC");

    @Override
    public BiPredicate<HashAlgo, Key> predicate() {
        return (algo, key) -> {
            final String keyAlgo = key.getAlgorithm();
            return HashAlgo.SHA256.equals(algo) && ACCEPTED_ALGORITHMS.contains(keyAlgo);
        };
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public Signature generate() throws GeneralSecurityException {
        return Signature.getInstance(ALGO_NAME_ECDSA);
    }
}
