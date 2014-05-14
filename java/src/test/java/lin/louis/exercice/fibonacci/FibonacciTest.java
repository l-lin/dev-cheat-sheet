package lin.louis.exercice.fibonacci;

import exercice.fibonacci.Fibonacci;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class FibonacciTest {
    @Test
    public void testFibonacci() {
        assertThat(Fibonacci.fibonacci(0)).isEqualTo(1);
        assertThat(Fibonacci.fibonacci(1)).isEqualTo(1);
        assertThat(Fibonacci.fibonacci(6)).isEqualTo(13);
        assertThat(Fibonacci.fibonacci(11)).isEqualTo(144);
    }

    @Test
    public void testFibonacciRecursive() {
        assertThat(Fibonacci.fibonacciRecursive(0)).isEqualTo(1);
        assertThat(Fibonacci.fibonacciRecursive(1)).isEqualTo(1);
        assertThat(Fibonacci.fibonacciRecursive(6)).isEqualTo(13);
        assertThat(Fibonacci.fibonacciRecursive(11)).isEqualTo(144);
    }
}
