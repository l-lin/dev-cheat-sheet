package lin.louis.data_structures.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class CountDuplicateItems {

    @Test
    void countDuplicateItems() {
        List<String> list = Arrays.asList("A", "B", "C", "A", "D", "C", "A", "A", "B", "B");
        assertThat(Collections.frequency(list, "A")).isEqualTo(4);
        assertThat(Collections.frequency(list, "B")).isEqualTo(3);
        assertThat(Collections.frequency(list, "C")).isEqualTo(2);
        assertThat(Collections.frequency(list, "D")).isEqualTo(1);
    }
}
