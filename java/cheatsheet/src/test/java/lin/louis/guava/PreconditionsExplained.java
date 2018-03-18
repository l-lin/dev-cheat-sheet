package lin.louis.guava;

import com.google.common.base.Preconditions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://code.google.com/p/guava-libraries/wiki/PreconditionsExplained
 *
 * It's better to throw fast rather than having side effects!
 *
 * @author Oodrive
 * @author llin
 * @created 31/10/14 10:12
 */
public class PreconditionsExplained {
    @Test(expected = NullPointerException.class)
    public void checkNotNull() {
        String str = Preconditions.checkNotNull("foobar", "The string %s must be defined!", "str");
        assertThat(str).isNotNull();

        // This throw an exception
        str = Preconditions.checkNotNull(null, "The string %s must be defined!", "str");
        assertThat(str).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgument() {
        Preconditions.checkArgument("f".length() > 1, "The string %s must have a length > 1", "str");
    }

    @Test(expected = IllegalStateException.class)
    public void checkState() {
        Preconditions.checkState("f".length() > 1, "The string %s must have a length > 1", "str");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkElementIndex() {
        // Check index from 0 inclusive to size EXCLUSIVE
        int index = Preconditions.checkElementIndex(3, 4);
        assertThat(index).isEqualTo(3);

        // This throw an exception
        Preconditions.checkElementIndex(4, 4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkPositionIndex() {
        // Check index from 0 inclusive to size INCLUSIVE
        int index = Preconditions.checkPositionIndex(4, 4);
        assertThat(index).isEqualTo(4);

        Preconditions.checkPositionIndex(5, 4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkPositionIndexes() {
        Preconditions.checkPositionIndexes(0, 10, 4);
    }
}
