package lin.louis.board;

import common.BooleanBoard;
import org.junit.Before;
import org.junit.Test;

public class BooleanBoardTest {
    private static final int NB_COLUMNS = 10;
    private static final int NB_ROWS = 10;
    private BooleanBoard board;

    @Before
    public void setUp() {
        board = new BooleanBoard(NB_COLUMNS, NB_ROWS);
    }

    @Test
    public void testToString() {
        System.out.println(board.toString());
    }
}
