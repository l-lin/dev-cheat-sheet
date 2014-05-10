package lin.louis.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author llin
 * @created 09/05/14.
 */
public class ArrayToList {
    @Test
    public void toList() {
        String[] array = new String[]{"foo", "bar", "foobar"};
        List<String> list = Arrays.asList(array);
        assertThat(list).isNotNull().isNotEmpty().contains("foo", "bar", "foobar");
    }
}
