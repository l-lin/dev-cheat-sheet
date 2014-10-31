package lin.louis.guava;

import com.google.common.base.Throwables;
import org.junit.Test;

/**
 * https://code.google.com/p/guava-libraries/wiki/ThrowablesExplained
 *
 * @author Oodrive
 * @author llin
 * @created 31/10/14 10:31
 */
public class ThrowablesExplained {
    @Test(expected = RuntimeException.class)
    public void propagation() {
        try {
            throw new Exception("foobar");
        } catch (Exception e) {
            // Throw a RuntimeException => No need to rewrite the signature of the methods
            Throwables.propagateIfPossible(e);
            throw Throwables.propagate(e);
        }
    }
}
