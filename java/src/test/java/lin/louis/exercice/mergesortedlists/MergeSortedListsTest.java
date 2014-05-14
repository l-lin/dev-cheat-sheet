package lin.louis.exercice.mergesortedlists;

import exercice.mergesortedlists.MergeSortedLists;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class MergeSortedListsTest {
    @Test
    public void testMergeSortedLists() {
        List<Integer> list1 = newArrayList(1, 2, 4, 6, 8);
        List<Integer> list2 = newArrayList(3, 5, 7, 9, 12);
        List<Integer> list = MergeSortedLists.mergeSortedLists(list1, list2);

        assertThat(list).isEqualTo(newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 12));
    }
}
