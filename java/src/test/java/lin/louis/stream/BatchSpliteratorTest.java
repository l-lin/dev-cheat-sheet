package lin.louis.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class BatchSpliteratorTest {

    @Test
    void givenSingleDivisibleListByBatchSize_whenSpliting_thenSplitCorrectly() {
        List<Integer> list = List.of(1, 2, 3);

        List<List<Integer>> result = BatchSpliterator.batched(list, 3)
                .map(group -> group.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, result.size());
        Assertions.assertIterableEquals(List.of(1, 2, 3), result.get(0));
    }

    @Test
    void givenDivisibleListByBatchSize_whenSpliting_thenSplitCorrectly() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<List<Integer>> result = BatchSpliterator.batched(list, 3)
                .map(group -> group.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(3, result.size());
        Assertions.assertIterableEquals(List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        ), result);
    }

    @Test
    void givenNotDivisibleListByBatchSize_whenSpliting_thenSplitCorrectly() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        List<List<Integer>> result = BatchSpliterator.batched(list, 3)
                .map(group -> group.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(3, result.size());
        Assertions.assertIterableEquals(List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(6, 7, 8)
        ), result);
    }

    @Test
    void givenListSizeLowerThanBatchSize_whenSpliting_thenTakeWholeList() {
        List<Integer> list = List.of(1, 2);

        List<List<Integer>> result = BatchSpliterator.batched(list, 3)
                .map(group -> group.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, result.size());
        Assertions.assertIterableEquals(List.of(1, 2), result.get(0));
    }

    @Test
    void givenEmptyList_whenSplitting_thenReturnEmptyList() {
        List<Integer> list = Collections.emptyList();

        List<List<Integer>> result = BatchSpliterator.batched(list, 3)
                .map(group -> group.collect(Collectors.toList()))
                .collect(Collectors.toList());

        Assertions.assertTrue(result.isEmpty());
    }
}