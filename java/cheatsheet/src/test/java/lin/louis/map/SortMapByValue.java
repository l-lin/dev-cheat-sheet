package lin.louis.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SortMapByValue {
    @Test
    public void sortMapByValue() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Foo");
        map.put(3, "Bar");
        map.put(5, "Moliku");
        map.put(2, "Foobar");

        System.out.println("Unsorted map:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }

        map = sortMap(map);
        System.out.println("\nSorted map:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }
    }

    private Map<Integer, String> sortMap(Map<Integer, String> map) {
        List<Map.Entry<Integer, String>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
