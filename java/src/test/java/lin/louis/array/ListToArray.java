package lin.louis.array;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class ListToArray {
    @Test
    void toArray() {
        List<String> list = Arrays.asList("Foo", "Bar", "Foobar");
        String[] array = list.toArray(new String[0]);
        assertThat(array).isNotNull().isNotEmpty().contains("Foo", "Bar", "Foobar");
    }
}
