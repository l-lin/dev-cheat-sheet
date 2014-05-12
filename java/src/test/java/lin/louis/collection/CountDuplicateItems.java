package lin.louis.collection;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 12/05/14.
 */
public class CountDuplicateItems {
    @Test
    public void countDuplicateItems() {
        List<String> list = newArrayList("A", "B", "C", "A", "D", "C", "A", "D", "A", "B", "B");
        assertThat(Collections.frequency(list, "A")).isEqualTo(4);
        assertThat(Collections.frequency(list, "B")).isEqualTo(3);
        assertThat(Collections.frequency(list, "C")).isEqualTo(2);
        assertThat(Collections.frequency(list, "D")).isEqualTo(1);
    }
}
