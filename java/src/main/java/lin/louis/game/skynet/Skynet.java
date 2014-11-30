package lin.louis.game.skynet;

import java.util.*;
import java.io.*;
import java.math.*;

public class Skynet {
    private enum CMD {
        SPEED, SLOW, JUMP, WAIT
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // the length of the road before the gap.
        in.nextLine();
        int G = in.nextInt(); // the length of the gap.
        in.nextLine();
        int L = in.nextInt(); // the length of the landing platform.
        in.nextLine();

        // game loop
        while (true) {
            int S = in.nextInt(); // the motorbike's speed.
            in.nextLine();
            int X = in.nextInt(); // the position on the road of the motorbike.
            in.nextLine();

            CMD cmd;
            if (isBeforeGap(R, X)) {
                if (shouldJump(R, X, S, G)) {
                    cmd = CMD.JUMP;
                } else {
                    if (isSpeedEnough(S, G)) {
                        cmd = shouldSlowDown(S, G) ? CMD.SLOW : CMD.WAIT;
                    } else {
                        cmd = CMD.SPEED;
                    }
                }
            } else {
                cmd = CMD.SLOW;
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(cmd); // A single line containing one of 4 keywords: SPEED, SLOW, JUMP, WAIT.
        }
    }

    private static boolean isBeforeGap(int R, int X) {
        return R - X > 0;
    }

    private static boolean isSpeedEnough(int S, int G) {
        return S > G;
    }

    private static boolean shouldSlowDown(int S, int G) {
        return S > G + 1;
    }

    private static boolean shouldJump(int R, int X, int S, int G) {
        int nextR = X + S;
        int roadPlusGap = R + G;
        return nextR - roadPlusGap >= 0;
    }

}
