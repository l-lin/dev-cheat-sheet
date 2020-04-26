package lin.louis.map;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;


class SortMapByKey {
    @Test
    void sortMapByKey() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Foo");
        map.put(3, "Bar");
        map.put(5, "Moliku");
        map.put(2, "Foobar");

        System.out.println("Unsorted map:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }

        // Sort by key using Treemap
        map = new TreeMap<>(map);
        System.out.println("\nSorted map:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }
    }
}
