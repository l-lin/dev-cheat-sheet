package lin.louis.collection;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 13/08/14 18:20
 */
public class MaxFromList {
    @Test
    public void getMaxFromList() {
        List<Integer> list = newArrayList(2, 3, 1, 9, 4);
        assertThat(Collections.max(list)).isEqualTo(9);
    }
}
