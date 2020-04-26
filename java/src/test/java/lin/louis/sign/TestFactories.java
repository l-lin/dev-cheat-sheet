package lin.louis.sign;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import lin.louis.sign.HashAlgo;
import lin.louis.sign.supplier.Sha256WithEcdsaSignatureSupplier;
import lin.louis.sign.supplier.Sha256WithRsaSignatureSupplier;
import lin.louis.sign.supplier.SignatureSupplier;
import lin.louis.sign.supplier.SignatureSupplierFactory;


public class TestFactories {

    private TestFactories() {}

    public static SignatureSupplierFactory signatureSupplierFactory() {
        SignatureSupplier sha256WithRsaSignatureSupplier = new Sha256WithRsaSignatureSupplier();
        SignatureSupplier sha256WithEcdaSignatureSupplier = new Sha256WithEcdsaSignatureSupplier();

        Map<BiPredicate<HashAlgo, Key>, SignatureSupplier> suppliers = new HashMap<>();
        suppliers.put(sha256WithRsaSignatureSupplier.predicate(), sha256WithRsaSignatureSupplier);
        suppliers.put(sha256WithEcdaSignatureSupplier.predicate(), sha256WithEcdaSignatureSupplier);
        return new SignatureSupplierFactory(suppliers);
    }
}
