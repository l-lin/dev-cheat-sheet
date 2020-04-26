package lin.louis.exercice.armstrong;

public class Armstrong {
    public static boolean isArmstrong(int number) {
        if (number < 100 || number >= 1000) {
            return false;
        }

        int result = 0;
        int nb = number;
        do {
            int remaining = nb % 10;
            result += Math.pow(remaining, 3);
            nb = nb / 10;
        } while (nb > 0);

        return result == number;
    }
}
