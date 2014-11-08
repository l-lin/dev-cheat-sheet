package lin.louis.game.pathfinder;

import lin.louis.game.common.board.MatrixBooleanBoard;
import lin.louis.game.common.board.MatrixCharBoard;
import lin.louis.game.common.Point;
import lin.louis.game.common.pathfinder.BreadthFirstSearch;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BreadthFirstSearchTest {
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
    public void bfs() {
        BreadthFirstSearch.Node moves = BreadthFirstSearch.bfs(
                walls,
                new Point(0, HEIGHT - 1),
                new Point(WIDTH - 1, 0)
        );
        assertThat(moves).isNotNull();
        replace(board, moves);
        System.out.println(board.toString());
    }

    private void replace(MatrixCharBoard board, BreadthFirstSearch.Node moves) {
        while (moves != null) {
            board.set(moves.p, moves.direction.name().charAt(0));
            moves = moves.next;
        }
    }
}
