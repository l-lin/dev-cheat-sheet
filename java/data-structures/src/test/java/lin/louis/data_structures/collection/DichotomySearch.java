package lin.louis.data_structures.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DichotomySearch {

    @Test
    void dichotomySearch() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 5, 8, 17, 20, 22, 23);
        assertThat(isInList(list, 3)).isTrue();
        assertThat(isInList(list, 5)).isTrue();
        assertThat(isInList(list, 24)).isFalse();
        assertThat(isInList(list, 21)).isFalse();
    }

    private static boolean isInList(List<Integer> list, int nbToSearch) {
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
