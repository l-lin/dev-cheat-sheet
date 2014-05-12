package lin.louis.collection;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Index.atIndex;

/**
 * @author llin
 * @created 11/05/14.
 */
public class ReverseList {
    @Test
    public void reverseList() {
        List<String> list = newArrayList("Foo", "Bar", "Moliku", "Foobar");

        Collections.sort(list, Collections.reverseOrder());

        assertThat(list).isNotNull().contains("Foobar", atIndex(0))
                .contains("Moliku", atIndex(1))
                .contains("Bar", atIndex(2))
                .contains("Foo", atIndex(3));
    }
}
