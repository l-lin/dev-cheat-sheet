package lin.louis.data_structures.exercises.fizzbuzz;

public class FizzBuzz {
    public static void main(String[] argv) {
        System.out.println(fizzBuzz(" ", 100));
    }

    public static String fizzBuzz(String separator, int maxIteration) {
        StringBuilder sbFizzBuzz = new StringBuilder();
        for (int i = 1; i <= maxIteration; i++) {
            StringBuilder sb = new StringBuilder();
            if (i % 3 == 0) {
                sb.append("Fizz");
            }
            if (i % 5 == 0) {
                sb.append("Buzz");
            }
            if ("".equals(sb.toString())) {
                sb.append(i);
            }

            if (i < maxIteration) {
                sb.append(separator);
            }
            sbFizzBuzz.append(sb.toString());
        }
        return sbFizzBuzz.toString();
    }
}
