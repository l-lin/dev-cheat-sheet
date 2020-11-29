package lin.louis.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class SlidingWindowSpliteratorTest {

    @Test
    @DisplayName("Given larger list than window size, "
            + "when window splitting, "
            + "then generate 2 streams")
    void largerListThanWindowSize() {
        List<Integer> source = List.of(1, 2, 3, 4);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(2, result.size());
        Assertions.assertIterableEquals(List.of(List.of(1, 2, 3), List.of(2, 3, 4)), result);
    }

    @Test
    @DisplayName("Given smaller list than window size, "
            + "when window splitting, "
            + "then generate empty stream")
    void smallerListThanWindowSize() {
        List<Integer> source = List.of(1, 2);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given empty list, "
            + "when window splitting, "
            + "then generate empty stream")
    void givenEmptyList_whenWindowSplitting_thenGenerateEmptyStream() {
        List<Integer> source = Collections.emptyList();

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given window size 0, "
            + "when window splitting, "
            + "then generate empty stream")
    void windowSize0() {
        List<Integer> source = List.of(1, 2, 3, 4);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 0)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given window size smaller than given list, "
            + "when estimating size, "
            + "then should estimate correctly")
    void windowSizeSmallerThanGivenList() {
        List<Integer> source = List.of(1, 2, 3, 4);

        long result = SlidingWindowSpliterator.windowed(source, 3)
                .spliterator().estimateSize();

        Assertions.assertEquals(2, result);
    }

    @Test
    @DisplayName("Given window size too big, "
            + "when estimating size, "
            + "then should return 0")
    void windowSizeTooBig() {
        List<Integer> source = List.of(1, 2, 3, 4);

        long result = SlidingWindowSpliterator.windowed(source, source.size() + 1)
                .spliterator().estimateSize();

        Assertions.assertEquals(0, result);
    }
}
