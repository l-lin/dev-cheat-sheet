package lin.louis.board;

import game.common.board.IntegerBoard;
import org.junit.Before;
import org.junit.Test;

public class IntegerBoardTest {
    private static final int NB_COLUMNS = 10;
    private static final int NB_ROWS = 10;

    private IntegerBoard board;

    @Before
    public void setUp() {
        board = new IntegerBoard(NB_COLUMNS, NB_ROWS);
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
