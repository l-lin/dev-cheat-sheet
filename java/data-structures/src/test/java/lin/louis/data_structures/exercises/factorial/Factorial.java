package lin.louis.data_structures.exercises.factorial;

public class Factorial {
    public static int factorial(int number) {
        if (number <= 1) {
            return 1;
        }

        int result = 1;
        do {
            result *= number--;
        } while(number > 1);

        return result;
    }

    public static int factorialRecursive(int number) {
        if (number <= 1) {
            return 1;
        }
        return number * factorialRecursive(number - 1);
    }
}
