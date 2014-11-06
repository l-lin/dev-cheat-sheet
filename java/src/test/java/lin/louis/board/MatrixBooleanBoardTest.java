package lin.louis.board;

import game.common.board.MatrixBooleanBoard;
import org.junit.Before;
import org.junit.Test;

public class MatrixBooleanBoardTest {
    private static final int NB_COLUMNS = 10;
    private static final int NB_ROWS = 10;
    private MatrixBooleanBoard board;

    @Before
    public void setUp() {
        board = new MatrixBooleanBoard(NB_COLUMNS, NB_ROWS);
    }

    @Test
    public void testToString() {
        System.out.println(board.toString());
    }
}
