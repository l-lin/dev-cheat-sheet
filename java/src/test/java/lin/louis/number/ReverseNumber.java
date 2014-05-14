package lin.louis.number;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class ReverseNumber {
    @Test
    public void reverseNumber() {
        int nb = 987654321;

        List<Integer> integerList = newArrayList();

        do {
            integerList.add(nb % 10);
            nb = nb / 10;
        } while (nb > 0);

        int nbReversed = 0;
        for (int i = 0; i < integerList.size(); i++) {
            nbReversed += integerList.get(i) * Math.pow(10, integerList.size() - 1 - i);
        }

        assertThat(nbReversed).isEqualTo(123456789);
    }

    @Test
    public void reverseNumberBetterSolution() {
        int nb = 987654321;
        int nbReversed = 0;

        do {
            int remainer = nb % 10;
            nbReversed = nbReversed * 10 + remainer;
            nb = nb / 10;
        } while (nb > 0);

        assertThat(nbReversed).isEqualTo(123456789);
    }
}
