package game.common.board;

public class BooleanBoard {
    private boolean[][] board;
    private int nbColumns;
    private int nbRows;

    public BooleanBoard(int nbColumns, int nbRows) {
        this.nbColumns = nbColumns;
        this.nbRows = nbRows;
        this.board = new boolean[nbColumns][nbRows];
    }

    public boolean get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, boolean value) {
        board[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuilder mapLine = new StringBuilder();

        for (int j = 0; j < nbRows; j++) {
            for (int i = 0; i < nbColumns; i++) {
                mapLine.append(get(i, j) ? "0" : "X").append(" ");
            }
            mapLine.append('\n');
        }

        return mapLine.toString();
    }
}
