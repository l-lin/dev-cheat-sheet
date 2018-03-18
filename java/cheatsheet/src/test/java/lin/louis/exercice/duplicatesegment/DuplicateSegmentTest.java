package lin.louis.exercice.duplicatesegment;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class DuplicateSegmentTest {
    @Test
    public void testHasDuplicateSegment() {
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDE")).isFalse();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCBA")).isTrue();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDQSDACBP")).isTrue();
        assertThat(DuplicateSegment.hasDuplicateSegment("ABCDQSDACVA")).isFalse();
    }
}
