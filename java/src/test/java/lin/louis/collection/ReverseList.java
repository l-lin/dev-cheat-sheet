package lin.louis.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;


class ReverseList {
    @Test
    void reverseList() {
        List<String> list = Arrays.asList("Foo", "Bar", "Moliku", "Foobar");
        Collections.reverse(list);

        assertThat(list).isNotNull().contains("Foobar", atIndex(0))
                .contains("Moliku", atIndex(1))
                .contains("Bar", atIndex(2))
                .contains("Foo", atIndex(3));
    }

    @Test
    void reverseOrderList() {
        List<String> list = Arrays.asList("Foo", "Bar", "Moliku", "Foobar");

        list.sort(Collections.reverseOrder());

        assertThat(list).isNotNull().contains("Moliku", atIndex(0))
                .contains("Foobar", atIndex(1))
                .contains("Foo", atIndex(2))
                .contains("Bar", atIndex(3));
    }
}
