package lin.louis.exercice.duplicatesegment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DuplicateSegmentTest {
    @Test
    public void testHasDuplicateSegment() {
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDE")).isFalse();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCBA")).isTrue();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDQSDACBP")).isTrue();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDQSDACVA")).isFalse();
    }
}
