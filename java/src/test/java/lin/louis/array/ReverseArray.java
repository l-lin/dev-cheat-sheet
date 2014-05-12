package lin.louis.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Index.atIndex;

/**
 * @author llin
 * @created 11/05/14.
 */
public class ReverseArray {
    @Test
    public void reverseOrderArray() {
        String[] array = new String[]{"Foo", "Bar", "Moliku", "Foobar"};

        Arrays.sort(array, Collections.reverseOrder());

        assertThat(array).isNotNull().contains("Moliku", atIndex(0))
                .contains("Foobar", atIndex(1))
                .contains("Foo", atIndex(2))
                .contains("Bar", atIndex(3));
    }
}
