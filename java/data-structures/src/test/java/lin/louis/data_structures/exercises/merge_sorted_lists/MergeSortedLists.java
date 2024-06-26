package lin.louis.data_structures.exercises.merge_sorted_lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSortedLists {
    public static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        List<Integer> list = new ArrayList<>(list1);
        list.addAll(list2);
        Collections.sort(list);
        return list;
    }
}
