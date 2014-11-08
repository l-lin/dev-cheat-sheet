package lin.louis.game.onboarding;

import java.util.Scanner;

public class OnBoardingPlayer {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int count = in.nextInt(); // The number of current enemy ships within range
        in.nextLine();

        int minDist = Integer.MAX_VALUE;
        String closestEnemy = "";

        for (int i = 0; i < count; i++) {
            String enemy = in.next(); // The name of this enemy
            int dist = in.nextInt(); // The distance to your cannon of this enemy

            if (dist < minDist) {
                minDist = dist;
                closestEnemy = enemy;
            }

            in.nextLine();
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(closestEnemy);
    }
}
