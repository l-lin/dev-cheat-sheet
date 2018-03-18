package lin.louis.exercice.factorial;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 15/05/14.
 */
public class FactorialTest {
    @Test
    public void testFactorial() {
        assertThat(Factorial.factorial(3)).isEqualTo(2 * 3);
        assertThat(Factorial.factorial(4)).isEqualTo(2 * 3 * 4);
        assertThat(Factorial.factorial(5)).isEqualTo(2 * 3 * 4 * 5);
        assertThat(Factorial.factorial(6)).isEqualTo(2 * 3 * 4 * 5 * 6);
    }

    @Test
    public void testFactorialRecursive() {
        assertThat(Factorial.factorialRecursive(3)).isEqualTo(2 * 3);
        assertThat(Factorial.factorialRecursive(4)).isEqualTo(2 * 3 * 4);
        assertThat(Factorial.factorialRecursive(5)).isEqualTo(2 * 3 * 4 * 5);
        assertThat(Factorial.factorialRecursive(6)).isEqualTo(2 * 3 * 4 * 5 * 6);
    }
}
