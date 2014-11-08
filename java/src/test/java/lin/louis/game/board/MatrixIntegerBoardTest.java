package lin.louis.game.board;

import lin.louis.game.common.board.MatrixIntegerBoard;
import org.junit.Before;
import org.junit.Test;

public class MatrixIntegerBoardTest {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private MatrixIntegerBoard board;

    @Before
    public void setUp() {
        board = new MatrixIntegerBoard(WIDTH, HEIGHT);
    }

    @Test
    public void testToString() {
        System.out.println(board.toString());

        board.set(0, 2, 4);
        board.set(0, 7, 2);
        board.set(2, 2, 1);
        board.set(9, 2, 4);
        board.set(8, 7, 5);
        board.set(7, 9, 6);
        board.set(8, 7, 8);
        System.out.println(board.toString());
    }
}
