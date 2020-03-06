package lin.louis.exercice.reversefizzbuzz;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ReverseFizzBuzzTest {
    private static final String INPUT_1 = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 Buzz 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 Buzz 49 50 51 52 53 Buzz 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 Buzz 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 Buzz 97 98 99 100";

    private static final String OUTPUT_1 = "24 54";

    private static final String INPUT_2 = "1 2 3 4 5 6 Buzz 8 9 10 11 12 13 Buzz 15 16 17 18 19 20 Buzz 22 23 24 25 26 27 Buzz 29 30 31 32 33 34 Buzz 36 37 38 39 40 41 Buzz 43 44 45 46 47 48 Buzz 50 51 52 53 54 55 Buzz 57 58 59 60 61 62 Buzz 64 65 66 67 Buzz 69 Buzz 71 72 73 74 75 76 Buzz 78 79 80 81 82 83 Buzz 85 86 87 88 89 90 Buzz 92 93 94 95 96 97 Buzz 99 100";

    private static final String OUTPUT_2 = "7 68";

    @Test
    public void testFizzBuzz() {
        assertThat(ReverseFizzBuzz.reverseFizzBuzz(INPUT_1)).isEqualTo(OUTPUT_1);
        assertThat(ReverseFizzBuzz.reverseFizzBuzz(INPUT_2)).isEqualTo(OUTPUT_2);
    }
}
