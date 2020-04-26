package lin.louis.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class ArrayToList {
    @Test
    void toList() {
        String[] array = new String[]{"foo", "bar", "foobar"};
        List<String> list = Arrays.asList(array);
        assertThat(list).isNotNull().isNotEmpty().contains("foo", "bar", "foobar");
    }
}
