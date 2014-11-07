package lin.louis.pathfinder;

import game.common.board.MatrixBooleanBoard;
import game.common.board.MatrixCharBoard;
import game.common.command.FourDirection;
import game.common.entities.Point;
import game.common.pathfinder.AntPathFinder;
import game.common.pathfinder.BreadthFirstSearch;
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
        board = new MatrixCharBoard(WIDTH, HEIGHT, '.');
        walls = new MatrixBooleanBoard(WIDTH, HEIGHT);
        walls.initBoard(true);
        walls.set(WIDTH - 1, 1, false);
        board.set(WIDTH - 1, 1, '#');
        System.out.println(walls.toString());
    }

    @Test
    public void ant() {
        AntPathFinder pathFinder = new AntPathFinder(200);
        FourDirection[] moves = pathFinder.findPath(
                walls,
                0, HEIGHT - 1,
                WIDTH - 1, 0
        );

        assertThat(moves).isNotNull();
        replace(board, moves, new Point(0, HEIGHT - 1));
        System.out.println(board.toString());
    }

    private void replace(MatrixCharBoard board, FourDirection[] moves, Point p) {
        Point currentPoint = p;
        for (FourDirection move : moves) {
            currentPoint = currentPoint.add(move);
            board.set(currentPoint, move.name().charAt(0));
        }
    }

    private FourDirection[] toFourDirection(int[] path) {
        FourDirection[] directions = new FourDirection[path.length];
        for (int i = 0; i < path.length; i++) {
            switch(path[i]) {
                case 0:
                    directions[i] = FourDirection.N;
                    break;
                case 1:
                    directions[i] = FourDirection.E;
                    break;
                case 2:
                    directions[i] = FourDirection.S;
                    break;
                case 3:
                    directions[i] = FourDirection.W;
                    break;
                default:
                    break;
            }
        }
        return directions;
    }
}
