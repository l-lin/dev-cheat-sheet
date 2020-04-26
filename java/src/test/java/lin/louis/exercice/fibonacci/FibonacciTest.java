package lin.louis.exercice.fibonacci;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class FibonacciTest {
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
