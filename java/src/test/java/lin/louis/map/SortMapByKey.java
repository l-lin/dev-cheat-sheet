package lin.louis.map;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author llin
 * @created 12/05/14.
 */
public class SortMapByKey {
    @Test
    public void sortMapByKey() {
        Map<Integer, String> map = newHashMap();
        map.put(1, "Foo");
        map.put(3, "Bar");
        map.put(5, "Moliku");
        map.put(2, "Foobar");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }

        // Sort by key using Treemap
        map = new TreeMap<>(map);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }
    }
}
