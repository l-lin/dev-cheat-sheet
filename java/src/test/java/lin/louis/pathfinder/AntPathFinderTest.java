package lin.louis.pathfinder;

import game.common.board.MatrixBooleanBoard;
import game.common.board.MatrixCharBoard;
import game.common.command.FourDirection;
import game.common.entities.Point;
import game.common.pathfinder.AntPathFinder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AntPathFinderTest {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 15;
    private MatrixBooleanBoard walls;
    private MatrixCharBoard board;

    @Before
    public void setUp() {
        walls = new MatrixBooleanBoard(WIDTH, HEIGHT);
    }

    @Test
    public void ant() {
        walls.initBoard(true);
        walls.set(WIDTH - 1, 1, false);
        board = new MatrixCharBoard(WIDTH, HEIGHT, '.');
        board.set(WIDTH - 1, 1, '#');
        System.out.println(walls.toString());

        AntPathFinder pathFinder = new AntPathFinder();
        FourDirection[] moves = pathFinder.findPath(
                walls,
                0, HEIGHT - 1,
                WIDTH - 1, 0
        );

        assertThat(moves).isNotNull();
        replace(board, moves, new Point(0, HEIGHT - 1));
        System.out.println(board.toString());
    }

    @Test
    public void ant2() {
        board = new MatrixCharBoard(WIDTH, HEIGHT, '#');
        for (int x = 5; x <= 15; x++) {
            walls.set(x, 6, true);
            board.set(x, 6, '.');
        }
        System.out.println(walls.toString());
        AntPathFinder pathFinder = new AntPathFinder();
        FourDirection[] moves = pathFinder.findPath(
                walls,
                5, 6,
                15, 6
        );

        assertThat(moves).isNotNull();
        replace(board, moves, new Point(5, 6));
        System.out.println(board.toString());
    }

    private void replace(MatrixCharBoard board, FourDirection[] moves, Point p) {
        Point currentPoint = p;
        for (FourDirection move : moves) {
            currentPoint = currentPoint.add(move);
            board.set(currentPoint, move.name().charAt(0));
        }
    }
}
