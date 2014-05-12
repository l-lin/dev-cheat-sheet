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
    public void reverseArray() {
        String[] array = new String[]{"Foo", "Bar", "Moliku", "Foobar"};

        Arrays.sort(array, Collections.reverseOrder());

        assertThat(array).isNotNull().contains("Foobar", atIndex(0))
                .contains("Moliku", atIndex(1))
                .contains("Bar", atIndex(2))
                .contains("Foo", atIndex(3));
    }
}
