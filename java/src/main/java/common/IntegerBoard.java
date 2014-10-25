package common;

public class IntegerBoard {
    private static final int INITIAL_VALUE = 0;
    private int[][] board;
    private int nbColumns;
    private int nbRows;

    public IntegerBoard(int nbColumns, int nbRows) {
        this.board = new int[nbColumns][nbRows];
        for (int x = 0; x < nbColumns; x++) {
            for (int y = 0; y < nbRows; y++) {
                this.board[x][y] = INITIAL_VALUE;
            }
        }
        this.nbColumns = nbColumns;
        this.nbRows = nbRows;
    }

    public int get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, int val) {
        board[x][y] = val;
    }

    @Override
    public String toString() {
        StringBuilder mapLine = new StringBuilder();

        for (int j = 0; j < nbRows; j++) {
            for (int i = 0; i < nbColumns; i++) {
                mapLine.append(get(i, j)).append(" ");
            }
            mapLine.append('\n');
        }

        return mapLine.toString();
    }
}
