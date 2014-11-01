package game.onboarding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import game.Game;
import game.YouLose;
import game.YouWin;

public class OnBoardingGame extends Game {

    private static final int MAX_ROUND = 30;

    public OnBoardingGame() {
        super();
    }

    public static void main(String... args) throws IOException {
        OnBoardingGame game = new OnBoardingGame();
        game.startStage1();
    }

    public void startStage1() throws IOException {
        int round = 1;

        Queue<Enemy> enemyQueue = new PriorityQueue<>();
        enemyQueue.add(new Enemy("HotDroid", 60, 1));
        enemyQueue.add(new Enemy("HotDroid", 60, 1));
        enemyQueue.add(new Enemy("HotDroid", 60, 1));
        enemyQueue.add(new Enemy("HotDroid", 60, 1));
        enemyQueue.add(new Enemy("Buzz", 70, 3));
        do {
            String[] input = buildInput(enemyQueue, round);
            setInput(input);

            OnBoardingPlayer.main();

            String enemyToEliminate = formatOutput(readOutput());
            System.err.println(enemyToEliminate);

            Enemy removedEnemy = findEnemy(enemyQueue, enemyToEliminate);
            if (removedEnemy != null) {
                enemyQueue.remove(removedEnemy);
                System.err.println(String.format("%s has been targeted", removedEnemy.getName()));
            } else {
                System.err.println(String.format("No enemy found for %s", enemyToEliminate));
            }

            if (enemyQueue.size() == 0) {
                throw new YouWin();
            }

            advanceEnemy(enemyQueue);

            round++;
        } while (round < MAX_ROUND);
    }

    // ----------------------------------

    private void advanceEnemy(Queue<Enemy> enemyQueue) {
        for (Enemy enemy : enemyQueue) {
            enemy.setDistance(enemy.getDistance() - 10);
            if (enemy.getDistance() < 0) {
                throw new YouLose();
            }
        }
    }

    private String[] buildInput(Queue<Enemy> enemyQueue, int round) {
        List<Enemy> enemyListInThisRound = new ArrayList<>();
        for (Enemy enemy : enemyQueue) {
            if (enemy.getRound() <= round) {
                enemyListInThisRound.add(enemy);
            }
        }

        List<String> inputList = new ArrayList<>();
        inputList.add(Integer.toString(enemyListInThisRound.size()));
        for (Enemy enemy : enemyListInThisRound) {
            inputList.add(enemy.getName() + " " + Integer.toString(enemy.getDistance()));
        }
        return inputList.toArray(new String[inputList.size()]);
    }

    private static Enemy findEnemy(Queue<Enemy> enemyQueue, String enemyToEliminate) {
        for (Enemy enemy : enemyQueue) {
            if (enemy.getName().equals(enemyToEliminate)) {
                return enemy;
            }
        }
        return null;
    }

    private static String formatOutput(String output) {
        return output.replace("\n", "").replace("\r", "");
    }
}
