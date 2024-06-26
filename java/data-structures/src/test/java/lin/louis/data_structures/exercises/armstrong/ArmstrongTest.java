package lin.louis.data_structures.exercises.armstrong;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class ArmstrongTest {
    @Test
    void testArmstrong() {
        assertThat(Armstrong.isArmstrong(153)).isTrue();
        assertThat(Armstrong.isArmstrong(371)).isTrue();
        assertThat(Armstrong.isArmstrong(123)).isFalse();
        assertThat(Armstrong.isArmstrong(1)).isFalse();
        assertThat(Armstrong.isArmstrong(12)).isFalse();
        assertThat(Armstrong.isArmstrong(1234)).isFalse();
    }
}
