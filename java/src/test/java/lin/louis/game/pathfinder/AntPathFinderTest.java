package lin.louis.game.pathfinder;

import lin.louis.game.common.board.MatrixBooleanBoard;
import lin.louis.game.common.board.MatrixCharBoard;
import lin.louis.game.common.command.FourDirection;
import lin.louis.game.common.Point;
import lin.louis.game.common.pathfinder.AntPathFinder;
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
                new Point(0, HEIGHT - 1),
                new Point(WIDTH - 1, 0)
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
                new Point(5, 6),
                new Point(15, 6)
        );

        assertThat(moves).isNotNull();
        replace(board, moves, new Point(5, 6));
        System.out.println(board.toString());
    }

    @Test
    public void ant3() {
        board = new MatrixCharBoard(WIDTH, HEIGHT, '.');
        walls.initBoard(true);
        for (int x = 0; x < WIDTH; x++) {
            walls.set(x, 0, false);
            board.set(x, 0, '#');
            walls.set(x, HEIGHT - 1, false);
            board.set(x, HEIGHT - 1, '#');
        }
        for (int y = 0; y < HEIGHT; y++) {
            walls.set(0, y, false);
            board.set(0, y, '#');
            walls.set(WIDTH - 1, y, false);
            board.set(WIDTH - 1, y, '#');
        }
        walls.set(1, 2, false);
        board.set(1, 2, '#');
        System.out.println(walls.toString());

        AntPathFinder pathFinder = new AntPathFinder();
        FourDirection[] moves = pathFinder.findPath(
                walls,
                new Point(28, 13),
                new Point(1, 1)
        );

        assertThat(moves).isNotNull();
        replace(board, moves, new Point(28, 13));
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
