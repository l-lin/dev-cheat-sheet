package lin.louis.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class MaxFromList {
    @Test
    public void getMaxFromList() {
        List<Integer> list = Arrays.asList(2, 3, 1, 9, 4);
        assertThat(Collections.max(list)).isEqualTo(9);
    }
}
