package lin.louis.data_structures.array;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class ReverseArray {

    @Test
    void reverseOrderArray() {
        String[] array = new String[] { "Foo", "Bar", "Moliku", "Foobar" };

        Arrays.sort(array, Collections.reverseOrder());

        assertThat(array).isNotNull().contains("Moliku", atIndex(0))
                .contains("Foobar", atIndex(1))
                .contains("Foo", atIndex(2))
                .contains("Bar", atIndex(3));
    }
}
