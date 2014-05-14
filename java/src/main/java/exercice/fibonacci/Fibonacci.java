package exercice.fibonacci;

/**
 * @author llin
 * @created 14/05/14.
 */
public class Fibonacci {
    public static int fibonacci(int nb) {
        if (nb <= 1) {
            return 1;
        }

        int result = 1;
        int prevResult = 1;
        for (int i = 2; i <= nb; i++) {
            int tmp = result;
            result += prevResult;
            prevResult = tmp;
        }
        return result;
    }

    public static int fibonacciRecursive(int nb) {
        if (nb <= 1) {
            return 1;
        }

        return fibonacci(nb - 1) + fibonacci(nb - 2);
    }
}
