package lin.louis.data_structures.exercises.reverse_number;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


class ReverseNumber {
    @Test
    void reverseNumber() {
        int nb = 987654321;

        List<Integer> integerList = new ArrayList<>();

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
    void reverseNumberBetterSolution() {
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
