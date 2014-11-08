package lin.louis.game.common.pathfinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lin.louis.game.common.board.MatrixBooleanBoard;
import lin.louis.game.common.command.FourDirection;
import lin.louis.game.common.Point;

public class AntPathFinder {
    private static final int MAX_SIZE = 1;
    private MatrixBooleanBoard walls;
    private MatrixBooleanBoard doneGrid;

    public FourDirection[] findPath(MatrixBooleanBoard walls, Point start, Point end) {
        init(walls);

        int size; //number of ants.
        Path ret = null;
        boolean done = false;
        List<AdamAnt> ants = new ArrayList<>(4);
        List<AdamAnt> babies = new ArrayList<>(100);

        // If the start or the goal is blocked, then there is a pb...
        if (this.walls.isBlocked(start) || this.walls.isBlocked(end)) {
            return null;
        }

        doneGrid.setBlocked(start); //set source as done.

        // The start is the end => No movement
        if (start.equals(end)) {
            return new FourDirection[0];
        } else {
            FourDirection[] order = makeOrder(start, end);
            ants.add(makeAnt(order[0], start));
        }
        while (!done) {
            size = ants.size();
            // Add babies to ants.
            while (size < MAX_SIZE && !babies.isEmpty()) {
                ants.add(babies.remove(0)); //babies.size()-1
                size++;
            }
            if (size == 0) break;
            for (int i = 0; i < size; i++) {
                AdamAnt a1;
                AdamAnt aa = ants.get(i);
                FourDirection[] order = makeOrder(aa.p, end);

                // Set the direction, remove this for nibbles-like behaviour.
                aa.setDir(order[0]);
                // Try to make ants going from this point in other directions.
                for (int j = 0; j < 4; j++) {
                    if (order[j] != aa.getDir()) {
                        a1 = (AdamAnt) aa.clone();
                        a1.setDir(order[j]);
                        if (a1.move()) babies.add(a1);
                    }
                }
                // Attempt to move. If can't move then remove it.
                if (!aa.move()) {
                    ants.remove(i);
                    size--;
                }
                // Check to see if we're finished.
                if (aa.p.equals(end)) {
                    done = true;
                    ret = aa.getPath();
                    break;
                }
            }
        }

        if (ret == null) {
            return null;
        }
        return ret.getArray();
    }

    private void init(MatrixBooleanBoard grid) {
        walls = grid.copy();
        doneGrid = new MatrixBooleanBoard(walls.width, walls.height);
        doneGrid.initBoard(true);
    }

    private AdamAnt makeAnt(FourDirection direction, Point p) {
        return (new AdamAnt(p, direction, walls, doneGrid));
    }

    private FourDirection[] makeOrder(Point p1, Point p2) {
        FourDirection[] ret = new FourDirection[4];
        int xAdd = 0;
        int yAdd = 0;
        if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
            xAdd = 1;
        } else {
            yAdd = 1;
        }
        if (p2.x > p1.x) {
            ret[1 - xAdd] = FourDirection.E;
            ret[2 + xAdd] = FourDirection.W;
        } else {
            ret[1 - xAdd] = FourDirection.W;
            ret[2 + xAdd] = FourDirection.E;
        }
        if (p2.y > p1.y) {
            ret[1 - yAdd] = FourDirection.S;
            ret[2 + yAdd] = FourDirection.N;
        } else {
            ret[1 - yAdd] = FourDirection.N;
            ret[2 + yAdd] = FourDirection.S;
        }
        return ret;
    }

    public static class Path extends LinkedList<FourDirection> {

        public Path() {
            super();
        }

        public FourDirection[] getArray() {
            FourDirection[] ret = new FourDirection[super.size()];
            int i = 0;
            for (Iterator it = super.iterator(); it.hasNext(); i++) {
                ret[i] = (FourDirection) it.next();
            }
            return ret;
        }

        @Override
        public boolean add(FourDirection direction) {
            return super.add(direction);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static class AdamAnt extends Path {
        private static MatrixBooleanBoard walls;
        private static MatrixBooleanBoard doneGrid;
        Point p;
        FourDirection dir;

        public AdamAnt(Point p, FourDirection d, MatrixBooleanBoard walls, MatrixBooleanBoard dgrid) {
            super();
            this.p = p;
            this.dir = d;
            AdamAnt.walls = walls;
            AdamAnt.doneGrid = dgrid;
        }

        public boolean move() {
            boolean moved = false;
            Point pointAfterMove = p.add(dir);
            if (isOkay(pointAfterMove.x, pointAfterMove.y)) {
                moved = true;
                doneGrid.setBlocked(pointAfterMove.x, pointAfterMove.y); // <--the space it's on.
                p = pointAfterMove;
                super.add(dir);
            }
            return moved;
        }

        protected boolean isOkay(int i, int j) {
            boolean okay = true;
            if (walls.isOutOfRange(i,j) || walls.isBlocked(i, j) || doneGrid.isBlocked(i, j)) {
                okay = false;
            }
            return okay;
        }

        @Override
        public Object clone() {
            AdamAnt ant = new AdamAnt(p, dir, walls, doneGrid);

            for (int i = 0; i < this.size(); i++) {
                ant.add(this.get(i));
            }
            return ant;
        }

        public void setDir(FourDirection d) {
            dir = d;
        }

        public FourDirection getDir() {
            return dir;
        }

        public Path getPath() {
            return this;
        }
    }
}
