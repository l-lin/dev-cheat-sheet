package lin.louis.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListToArray {

    @Test
    void toArray() {
        List<String> list = Arrays.asList("Foo", "Bar", "Foobar");

        String[] array = list.toArray(new String[0]);

        assertNotNull(array);
        assertEquals(list.size(), array.length);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), array[i]);
        }
    }
}
