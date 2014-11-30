package lin.louis.game.kirkquest;

import java.util.*;
import java.io.*;
import java.math.*;

public class KirkQuest {
    private enum CMD {
        HOLD, FIRE
    }

    private static final int MAX_SX = 7;
    private static final int MAX_SY = 10;
    private static final int MAX_MH = 9;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int SX = in.nextInt();
            int SY = in.nextInt();
            in.nextLine();
            int[] mountainHeights = new int[MAX_SX + 1];
            for (int i = 0; i <= MAX_SX; i++) {
                int MH = in.nextInt(); // represents the height of one mountain, from 9 to 0. Mountain heights are provided from left to right.
                in.nextLine();

                mountainHeights[i] = MH;
            }
            int maxMH = 0;
            for (int i = 0; i <= MAX_SX; i++) {
                if (mountainHeights[i] > mountainHeights[maxMH]) {
                    maxMH = i;
                }
            }
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            CMD cmd = SX == maxMH ? CMD.FIRE : CMD.HOLD;

            System.out.println(cmd); // either:  FIRE (ship is firing its phase cannons) or HOLD (ship is not firing).
        }
    }
}
