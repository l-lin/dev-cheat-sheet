package lin.louis.data_structures.exercises.merge_sorted_lists;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class MergeSortedListsTest {
    @Test
    void testMergeSortedLists() {
        List<Integer> list1 = Arrays.asList(1, 2, 4, 6, 8);
        List<Integer> list2 = Arrays.asList(3, 5, 7, 9, 12);
        List<Integer> list = MergeSortedLists.mergeSortedLists(list1, list2);

        assertThat(list).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 12));
    }
}
