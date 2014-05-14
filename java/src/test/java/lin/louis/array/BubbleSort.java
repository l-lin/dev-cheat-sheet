package lin.louis.array;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class BubbleSort {
    @Test
    public void bubbleSort() {
        int[] unsorted = {32, 39, 21, 45, 23, 3};
        sort(unsorted);
        int[] sorted = {3, 21, 23, 32, 39, 45};
        assertThat(unsorted).isEqualTo(sorted);
    }

    private void sort(int[] unsorted) {
        for (int i = 0; i < unsorted.length; i++) {
            for (int j = 1; j < unsorted.length - i; j++) {
                if (unsorted[j - 1] > unsorted[j]) {
                    int tmp = unsorted[j];
                    unsorted[j] = unsorted[j - 1];
                    unsorted[j - 1] = tmp;
                }
            }
        }
    }
}
