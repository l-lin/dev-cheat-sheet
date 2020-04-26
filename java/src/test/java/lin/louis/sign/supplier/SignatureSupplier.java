package lin.louis.sign.supplier;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Signature;
import java.util.function.BiPredicate;

import lin.louis.sign.HashAlgo;


public interface SignatureSupplier {
    BiPredicate<HashAlgo, Key> predicate();
    Signature generate() throws GeneralSecurityException;
}
