package lin.louis.data_structures.exercises.factorial;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class FactorialTest {
    @Test
    void testFactorial() {
        assertThat(Factorial.factorial(3)).isEqualTo(2 * 3);
        assertThat(Factorial.factorial(4)).isEqualTo(2 * 3 * 4);
        assertThat(Factorial.factorial(5)).isEqualTo(2 * 3 * 4 * 5);
        assertThat(Factorial.factorial(6)).isEqualTo(2 * 3 * 4 * 5 * 6);
    }

    @Test
    void testFactorialRecursive() {
        assertThat(Factorial.factorialRecursive(3)).isEqualTo(2 * 3);
        assertThat(Factorial.factorialRecursive(4)).isEqualTo(2 * 3 * 4);
        assertThat(Factorial.factorialRecursive(5)).isEqualTo(2 * 3 * 4 * 5);
        assertThat(Factorial.factorialRecursive(6)).isEqualTo(2 * 3 * 4 * 5 * 6);
    }
}
