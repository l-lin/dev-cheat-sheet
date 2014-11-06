package game.common.board;

import game.common.entities.Point;

public class MatrixBooleanBoard {
    private boolean[][] board;
    private int nbColumns;
    private int nbRows;

    public MatrixBooleanBoard(int nbColumns, int nbRows) {
        this.nbColumns = nbColumns;
        this.nbRows = nbRows;
        this.board = new boolean[nbColumns][nbRows];
    }

    public boolean get(int x, int y) {
        return board[x][y];
    }

    public boolean get(Point point) {
        return get(point.x, point.y);
    }

    public void set(int x, int y, boolean value) {
        board[x][y] = value;
    }

    public void set(Point point, boolean value) {
        set(point.x, point.y, value);
    }

    public boolean isBlocked(int x, int y) {
        return get(x, y);
    }

    public boolean isBlocked(Point p) {
        return isBlocked(p.x, p.y);
    }

    @Override
    public String toString() {
        StringBuilder mapLine = new StringBuilder();

        for (int y = 0; y < nbRows; y++) {
            for (int x = 0; x < nbColumns; x++) {
                mapLine.append(get(x, y) ? "0" : "X").append(" ");
            }
            mapLine.append('\n');
        }

        return mapLine.toString();
    }
}
