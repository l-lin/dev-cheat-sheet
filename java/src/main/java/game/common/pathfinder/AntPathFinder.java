package game.common.pathfinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.common.board.MatrixBooleanBoard;
import game.common.command.FourDirection;
import game.common.entities.Point;

/**
 * https://github.com/adamd/pathfinder
 */
public class AntPathFinder {
    // number of ants
    private static final int MAX_SIZE = 1;

    private int distance = Integer.MAX_VALUE;
    private MatrixBooleanBoard mygrid;
    private MatrixBooleanBoard doneGrid;


    public AntPathFinder() {
        this(30000);
    }

    /**
     * Initializes this pathfinder with a maximum possible distance for a path.
     *
     * @param maxDistance Maximum possible distance of a path in the grid.
     */
    public AntPathFinder(int maxDistance) {
        this.distance = maxDistance;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Finds a shortest path given an array and a starting point and end point.
     * Returns path in the form of an array of directions. <BR>
     * 0 = up, 1 = right, 2 = down, 3 = left.
     *
     * @param grid True means it is an obstacle.
     */
    public FourDirection[] findPath(MatrixBooleanBoard grid, int x1, int y1, int x2, int y2) {
        int size; //number of ants.
        Path ret = null;
        boolean done = false;
        List<AdamAnt> ants = new ArrayList<>(4);
        List<AdamAnt> babies = new ArrayList<>(100);

        if (hasProblem(grid, x1, y1, x2, y2)) {
            return null;
        }

        doneGrid.setBlocked(x1, y1); //set source as done.

        if (x1 == x2 && y1 == y2) {
            return (new FourDirection[0]);
        } else {
            FourDirection[] order = makeOrder(x1, y1, x2, y2);
            ants.add(makeAnt(order[0], x1, y1, x2, y2));//<----
        }
        while (!done) {
            size = ants.size();
            /*
             * Add babies to ants.
             */
            while (size < MAX_SIZE && !babies.isEmpty()) {
                ants.add(babies.remove(0)); //babies.size()-1
                size++;
            }
            if (size == 0) break;
            for (int i = 0; i < size; i++) {
                AdamAnt a1;
                AdamAnt aa = ants.get(i);
                FourDirection[] order = makeOrder(aa.getX(), aa.getY(), x2, y2);

                //Set the direction, remove this for nibbles-like behaviour.
                aa.setDir(order[0]);
                /*
                 * Try to make ants going from this point in other directions.
                 */
                for (int j = 0; j < 4; j++) {
                    if (order[j] != aa.getDir()) {
                        a1 = (AdamAnt) aa.clone();
                        a1.setDir(order[j]);
                        if (a1.move()) babies.add(a1);
                    }
                }
                /*
                 * Attempt to move. If can't move then remove it.
                 */
                if (!aa.move()) {
                    ants.remove(i);
                    size--;
                }
                /*
                 * Check to see if we're finished.
                 */
                if (aa.getX() == x2 && aa.getY() == y2) {
                    done = true;
                    ret = aa.getPath();
                    break;
                }
            }//i
        }//while

        if (ret == null) {
            return null;
        }
        return ret.getArray();
    }

    public boolean hasProblem(MatrixBooleanBoard grid, int x1, int y1, int x2, int y2) {
        boolean problem = false;

        /* Sanity check. */
        if (grid == null || grid.width == 0) {
            return true;
        }

        /* do initializing. */
        mygrid = grid;
        doneGrid = new MatrixBooleanBoard(mygrid.width, mygrid.height);
        doneGrid.initBoard(true);

        if (mygrid.isBlocked(x1, y1) || mygrid.isBlocked(x2, y2)) {
            problem = true;
        }

        return problem;
    }

    private AdamAnt makeAnt(FourDirection direction, int x1, int y1, int x2, int y2) {
        return (new AdamAnt(x1, y1, direction, mygrid, doneGrid));
    }

    private FourDirection[] makeOrder(int x1, int y1, int x2, int y2) {
        FourDirection[] ret = new FourDirection[4];
        int xAdd = 0;
        int yAdd = 0;
        if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
            xAdd = 1;
        } else {
            yAdd = 1;
        }
        if (x2 > x1) {
            ret[1 - xAdd] = FourDirection.E;
            ret[2 + xAdd] = FourDirection.W;
        } else {
            ret[1 - xAdd] = FourDirection.W;
            ret[2 + xAdd] = FourDirection.E;
        }
        if (y2 > y1) {
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

        public Path(List<FourDirection> list) {
            super(list);
        }

        public FourDirection[] getArray() {
            FourDirection[] ret = new FourDirection[super.size()];
            int i = 0;
            for (Iterator iter = super.iterator(); iter.hasNext(); i++) {
                ret[i] = (FourDirection) iter.next();
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
        private static MatrixBooleanBoard mygrid;
        private static MatrixBooleanBoard doneGrid;
        int x = 0;
        int y = 0;
        FourDirection dir;

        public AdamAnt(int x, int y, FourDirection d, MatrixBooleanBoard mygrid, MatrixBooleanBoard dgrid) {
            super();
            this.x = x;
            this.y = y;
            this.dir = d;
            AdamAnt.mygrid = mygrid;
            AdamAnt.doneGrid = dgrid;
        }

        public boolean move() {
            boolean moved = false;
            Point currentPoint = new Point(x, y);
            Point pointAfterMove = currentPoint.add(dir);
            if (isOkay(pointAfterMove.x, pointAfterMove.y)) {
                moved = true;
                doneGrid.setBlocked(pointAfterMove.x, pointAfterMove.y); //<--the space it's on.
                x = pointAfterMove.x;
                y = pointAfterMove.y;
                super.add(dir);
            }
            return moved;
        }//move

        protected boolean isOkay(int i, int j) {
            boolean okay = true;
            if (mygrid.isOutOfRange(i,j) || mygrid.isBlocked(i, j) || doneGrid.isBlocked(i, j)) {
                okay = false;
            }
            return okay;
        }

        @Override
        public Object clone() {
            AdamAnt ant = new AdamAnt(x, y, dir, mygrid, doneGrid);

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

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int n) {
            x = n;
        }

        public void setY(int n) {
            y = n;
        }

        public Path getPath() {
            return this;
        }
    }
}
