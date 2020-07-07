package lin.louis.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class SlidingWindowSpliteratorTest {

    @Test
    void givenLargerListThanWindowSize_whenWindowSplitting_thenGenerate2Streams() {
        List<Integer> source = List.of(1, 2, 3, 4);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(2, result.size());
        Assertions.assertIterableEquals(List.of(List.of(1, 2, 3), List.of(2, 3, 4)), result);
    }

    @Test
    void givenSmallerListThanWindow_whenWindowSplitting_thenGenerateEmptyStream() {
        List<Integer> source = List.of(1, 2);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenEmptyList_whenWindowSplitting_thenGenerateEmptyStream() {
        List<Integer> source = Collections.emptyList();

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 3)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenWindowSize0_whenWindowSplitting_thenGenerateEmptyStream() {
        List<Integer> source = List.of(1, 2, 3, 4);

        List<List<Integer>> result = SlidingWindowSpliterator.windowed(source, 0)
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenWindowSizeSmallerThanGivenList_whenEstimatingSize_thenShouldEstimateCorrectly() {
        List<Integer> source = List.of(1, 2, 3, 4);

        long result = SlidingWindowSpliterator.windowed(source, 3)
                .spliterator().estimateSize();

        Assertions.assertEquals(2, result);
    }

    @Test
    void givenWindowSizeTooBig_whenEstimatingSize_thenShouldReturn0() {
        List<Integer> source = List.of(1, 2, 3, 4);

        long result = SlidingWindowSpliterator.windowed(source, source.size() + 1)
                .spliterator().estimateSize();

        Assertions.assertEquals(0, result);
    }
}
