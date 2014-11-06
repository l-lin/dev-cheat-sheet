package game.common.board;

import java.util.Scanner;

import game.common.entities.Point;

public class MatrixCharBoard {
    private static final char DEFAULT = '?';

    public char[][] map;
    private int nbRows;
    private int nbColumns;

    public MatrixCharBoard(Scanner in, int nbRows, int nbColumns) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        map = new char[nbColumns][nbRows];
        for (int x = 0; x < nbColumns; x++) {
            String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
            System.arraycopy(ROW.toCharArray(), 0, map[x], 0, nbRows);
            in.nextLine();
        }
    }

    public MatrixCharBoard(int nbRows, int nbColumns) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        map = new char[nbColumns][nbRows];
        for (int x = 0; x < nbColumns; x++) {
            for (int y = 0; y < nbRows; y++) {
                map[x][y] = DEFAULT;
            }
        }
    }

    public boolean isBlocked(Point p){
        return map[p.x][p.y] == '#';
    }

    public boolean isUnknown(Point p) {
        return map[p.x][p.y] == '?';
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < nbRows; y++) {
            for (int x = 0; x < nbColumns; x++) {
                sb.append(map[x][y]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
