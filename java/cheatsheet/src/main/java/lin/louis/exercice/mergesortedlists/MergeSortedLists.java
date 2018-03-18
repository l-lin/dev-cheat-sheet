package lin.louis.exercice.mergesortedlists;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author llin
 * @created 14/05/14.
 */
public class MergeSortedLists {
    public static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        List<Integer> list = newArrayList(list1);
        list.addAll(list2);
        Collections.sort(list);
        return list;
    }
}
