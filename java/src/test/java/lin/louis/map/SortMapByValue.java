package lin.louis.map;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * @author llin
 * @created 12/05/14.
 */
public class SortMapByValue {
    @Test
    public void sortMapByValue() {
        Map<Integer, String> map = newHashMap();
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
        List<Map.Entry<Integer, String>> list = newLinkedList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Map<Integer, String> sortedMap = newLinkedHashMap();
        for (Map.Entry<Integer, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
