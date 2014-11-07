package game.common.board;

import game.common.entities.Point;

public class MatrixBooleanBoard {
    private boolean[][] board;
    public int width;
    public int height;

    public MatrixBooleanBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new boolean[width][height];
    }

    public void initBoard(boolean defaultValue) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                set(x, y, defaultValue);
            }
        }
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

    public void setBlocked(int x, int y) {
        set(x, y, false);
    }

    public void setBlocked(Point p) {
        setBlocked(p.x, p.y);
    }

    public boolean isBlocked(int x, int y) {
        return !get(x, y);
    }

    public boolean isBlocked(Point p) {
        return isBlocked(p.x, p.y);
    }

    public boolean isOutOfRange(int x, int y) {
        return x < 0 || y < 0 || x > width - 1 || y > height - 1;
    }

    public boolean isOutOfRange(Point p) {
        return isOutOfRange(p.x, p.y);
    }

    public MatrixBooleanBoard copy() {
        MatrixBooleanBoard clone = new MatrixBooleanBoard(width, height);
        for (int x = 0; x < width; x++) {
            System.arraycopy(board[x], 0, clone.board[x], 0, height);
        }

        return clone;
    }

    @Override
    public String toString() {
        StringBuilder mapLine = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapLine.append(get(x, y) ? "0" : "X").append(" ");
            }
            mapLine.append('\n');
        }

        return mapLine.toString();
    }
}
