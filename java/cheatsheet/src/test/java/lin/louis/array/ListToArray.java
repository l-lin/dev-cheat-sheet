package lin.louis.array;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 10/05/14.
 */
public class ListToArray {
    @Test
    public void toArray() {
        List<String> list = newArrayList("Foo", "Bar", "Foobar");
        String[] array = list.toArray(new String[list.size()]);
        assertThat(array).isNotNull().isNotEmpty().contains("Foo", "Bar", "Foobar");
    }
}
