package lin.louis.game.floodfill;

import lin.louis.game.common.board.MatrixBooleanBoard;
import lin.louis.game.common.board.MatrixIntegerBoard;
import lin.louis.game.common.Point;
import lin.louis.game.common.floodfill.FloodFill;
import org.junit.Before;
import org.junit.Test;

public class FloodFillTest {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 15;
    private MatrixBooleanBoard walls;

    @Before
    public void beforeEach() {
        walls = new MatrixBooleanBoard(WIDTH, HEIGHT);
    }

    @Test
    public void floodFill() {
        walls.initBoard(true);
        walls.set(WIDTH - 1, 1, false);
        MatrixIntegerBoard distanceBoard = FloodFill.floodFill(walls, new Point(0, HEIGHT - 1));
        System.out.print(distanceBoard.toString());
    }

    @Test
    public void floodFillWithWallsAround() {
        for (int x = 5; x <= 15; x++) {
            walls.set(x, 6, true);
        }
        MatrixIntegerBoard distanceBoard = FloodFill.floodFill(walls, new Point(5, 6));
        System.out.print(distanceBoard.toString());
    }
}
