package lin.louis.sign.supplier;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Signature;
import java.util.Map;
import java.util.function.BiPredicate;

import lin.louis.sign.HashAlgo;


public class SignatureSupplierFactory {

    private final Map<BiPredicate<HashAlgo, Key>, SignatureSupplier> suppliers;

    public SignatureSupplierFactory(Map<BiPredicate<HashAlgo, Key>, SignatureSupplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Signature create(HashAlgo algo, Key key) throws GeneralSecurityException {
        return getSignatureSupplier(algo, key).generate();
    }

    private SignatureSupplier getSignatureSupplier(HashAlgo algo, Key key) {
        return suppliers.entrySet().stream()
            .filter(e -> e.getKey().test(algo, key))
            .findAny()
            .orElseThrow(() -> new NullPointerException(
                "Could not find the signature supplier for hash algo " + algo + " and key algo " + key.getAlgorithm())
            )
            .getValue();
    }
}
