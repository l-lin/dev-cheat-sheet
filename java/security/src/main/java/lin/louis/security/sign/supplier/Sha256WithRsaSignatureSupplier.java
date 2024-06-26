package lin.louis.security.sign.supplier;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Security;
import java.security.Signature;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.function.BiPredicate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lin.louis.security.sign.HashAlgo;


public class Sha256WithRsaSignatureSupplier implements SignatureSupplier {

    private static final String ALGO_NAME_RSA = "SHA256withRSA/PSS";

    private static final String HASH_ALGO_NAME = "SHA-256";

    private static final String MASK_GENERATION_FUNCTION_ALGO_NAME = "MGF1";

    private static final int SALT_LEN = 32;

    private static final int TRAILER_FIELD = 1;

    private static final PSSParameterSpec PSS_PARAMETERS_PEC = new PSSParameterSpec(
        HASH_ALGO_NAME,
        MASK_GENERATION_FUNCTION_ALGO_NAME,
        new MGF1ParameterSpec(HASH_ALGO_NAME),
        SALT_LEN,
        TRAILER_FIELD
    );

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public BiPredicate<HashAlgo, Key> predicate() {
        return (algo, key) -> {
            final String keyAlgo = key.getAlgorithm();
            return HashAlgo.SHA256.equals(algo) && "RSA".equalsIgnoreCase(keyAlgo);
        };
    }

    @Override
    public Signature generate() throws GeneralSecurityException {
        final Signature signature = Signature.getInstance(ALGO_NAME_RSA);
        signature.setParameter(PSS_PARAMETERS_PEC);
        return signature;
    }
}
