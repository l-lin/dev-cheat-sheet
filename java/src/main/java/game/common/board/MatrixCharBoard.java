package game.common.board;

import java.util.Scanner;

import game.common.entities.Point;

public class MatrixCharBoard {
    private static final char DEFAULT_CHAR = '?';
    public char[][] map;
    public int width;
    public int height;

    public MatrixCharBoard(Scanner in, int width, int height) {
        this.height = height;
        this.width = width;
        map = new char[width][height];
        for (int y = 0; y < height; y++) {
            String ROW = in.next();
            char[] columns = ROW.toCharArray();
            for (int x = 0; x < width; x++) {
                map[x][y] = columns[x];
            }
            in.nextLine();
        }
    }

    public MatrixCharBoard(int width, int height) {
        this.height = height;
        this.width = width;
        initMap(DEFAULT_CHAR);
    }

    public MatrixCharBoard(int width, int height, char defaultChar) {
        this.height = height;
        this.width = width;
        initMap(defaultChar);
    }

    public boolean isBlocked(Point p){
        return map[p.x][p.y] == '#';
    }

    public boolean isUnknown(Point p) {
        return map[p.x][p.y] == '?';
    }

    public boolean isOutOfRange(int x, int y) {
        return x < 0 || y < 0 || x > width - 1 || y > height - 1;
    }

    public boolean isOutOfRange(Point p) {
        return isOutOfRange(p.x, p.y);
    }

    public void set(Point p, char value) {
        set(p.x, p.y, value);
    }

    public void set(int x, int y, char value) {
        map[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(map[x][y]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void initMap(char defaultChar) {
        map = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = defaultChar;
            }
        }
    }
}
