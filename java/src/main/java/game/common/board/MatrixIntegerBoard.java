package game.common.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import game.common.command.FourDirection;
import game.common.entities.Point;

public class MatrixIntegerBoard {
    private static final int INITIAL_VALUE = -1;
    private int[][] board;
    private int nbColumns;
    private int nbRows;

    public MatrixIntegerBoard(int nbColumns, int nbRows) {
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

    public int get(Point point) {
        return get(point.x, point.y);
    }

    public void set(int x, int y, int val) {
        board[x][y] = val;
    }

    public void set(Point point, int val) {
        set(point.x, point.y, val);
    }

    public int[][] floodFill(Point startPoint) {
        return floodFill(startPoint.x, startPoint.y);
    }

    public int[][] floodFill(int x, int y) {
        Queue<Point> edge = new LinkedList<>();
        edge.add(new Point(x, y));

        int currentDistance = 0;
        set(x, y, currentDistance);
        while (edge.size() > 0) {
            currentDistance++;
            List<Point> toCheck = new ArrayList<>();
            for (Point p : edge) {
                for (FourDirection direction : FourDirection.values()) {
                    Point newP = p.add(direction.x, direction.y);
                    if (isOutOfRange(newP) || isVisited(newP) || otherConditions(newP)) {
                        continue;
                    }
                    set(newP.x, newP.y, currentDistance);
                    toCheck.add(newP);
                }
            }
            edge.clear();
            edge.addAll(toCheck);
        }

        return board;
    }

    private boolean otherConditions(Point p) {
        // TODO: Implement me!
        return false;
    }

    private boolean isOutOfRange(int x, int y) {
        return x < 0 || y < 0 || x > nbColumns - 1 || y > nbRows - 1;
    }

    private boolean isOutOfRange(Point p) {
        return isOutOfRange(p.x, p.y);
    }

    private boolean isVisited(int x, int y) {
        return get(x, y) >= 0;
    }

    private boolean isVisited(Point p) {
        return isVisited(p.x, p.y);
    }

    @Override
    public String toString() {
        StringBuilder mapLine = new StringBuilder();

        for (int y = 0; y < nbRows; y++) {
            for (int x = 0; x < nbColumns; x++) {
                mapLine.append(get(x, y)).append("\t");
            }
            mapLine.append('\n');
        }

        return mapLine.toString();
    }
}
