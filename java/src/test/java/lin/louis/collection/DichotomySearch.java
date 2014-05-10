package lin.louis.collection;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.*;

/**
 * @author llin
 * @created 10/05/14.
 */
public class DichotomySearch {
    @Test
    public void dichotomySearch() {
        List<Integer> list = newArrayList(1, 2, 3, 4, 5, 5, 8, 17, 20, 22, 23);
        assertThat(isInList(list, 3)).isTrue();
        assertThat(isInList(list, 5)).isTrue();
        assertThat(isInList(list, 24)).isFalse();
        assertThat(isInList(list, 21)).isFalse();
    }

    public static boolean isInList(List<Integer> list, int nbToSearch) {
        int left = 0;
        int right = list.size() - 1;
        boolean isInList = false;
        int avg;
        while (left <= right && !isInList) {
            avg = (left + right) / 2;
            if (list.get(avg) == nbToSearch) {
                isInList = true;
            } else if (nbToSearch < list.get(avg)) {
                right = avg - 1;
            } else {
                left = avg + 1;
            }
        }
        return isInList;
    }
}
