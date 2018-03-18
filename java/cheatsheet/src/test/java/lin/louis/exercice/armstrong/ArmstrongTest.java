package lin.louis.exercice.armstrong;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class ArmstrongTest {
    @Test
    public void testArmstrong() {
        assertThat(Armstrong.isArmstrong(153)).isTrue();
        assertThat(Armstrong.isArmstrong(371)).isTrue();
        assertThat(Armstrong.isArmstrong(123)).isFalse();
        assertThat(Armstrong.isArmstrong(1)).isFalse();
        assertThat(Armstrong.isArmstrong(12)).isFalse();
        assertThat(Armstrong.isArmstrong(1234)).isFalse();
    }
}
