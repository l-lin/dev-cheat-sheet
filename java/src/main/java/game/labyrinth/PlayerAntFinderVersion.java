import java.util.*;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class PlayerAntFinderVersion {
    private static final boolean DEBUG = true;

    private static DistanceBoard distanceBoard;
    private static InputBoard inputBoard;
    private static MatrixBooleanBoard wallsBoard;
    private static Point pointC;
    private static Point pointT;
    private static boolean hasReachedC;
    private static Queue<Direction> moves;
    private static Queue<Direction> movesToC;
    private static Queue<Direction> movesToT;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // number of rows.
        int C = in.nextInt(); // number of columns.
        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        in.nextLine();

        int nbRounds = 1;

        // game loop
        while (true) {
            // Avoid freezing the browser...
            nbRounds++;
            if (nbRounds > 500) {
                break;
            }

            int ky = in.nextInt(); // row where Kirk is located.
            int kx = in.nextInt(); // column where Kirk is located.
            in.nextLine();

            debug("R = " + R + " - C = " + C);
            debug("kx = " + kx + " - ky = " + ky);

            wallsBoard = new MatrixBooleanBoard(C, R);
            inputBoard = new InputBoard(in, C, R, kx, ky, wallsBoard);
            distanceBoard = new DistanceBoard(C, R);
            hasReachedC = computeHasReachedC(hasReachedC, kx, ky);

            if (hasReachedC && movesToT == null) {
                // Going back to T
                movesToT = pathFinding(kx, ky, pointT);
            } else {
                if (hasFoundC() && movesToC == null) {
                    // Going to C
                    movesToC = pathFinding(kx, ky, pointC);
                }
                if (movesToC == null) {
                    if (moves == null || moves.isEmpty()) {
                        BestMove bestMove = floodFill(wallsBoard, kx, ky);
                        debug("BestMove = " + bestMove.toString());
                        moves = pathFinding(kx, ky, new Point(bestMove.x, bestMove.y));
                        debug(distanceBoard.toString());
                    }
                }
            }

            Direction direction;

            if (movesToT != null) {
                debug("---------------- GOING BACK TO T ----------------");
                inputBoard.replace(movesToT.toArray(new Direction[movesToT.size()]), new Point(kx, ky));
                direction = movesToT.remove();

            } else if (movesToC != null) {
                debug("---------------- GOING TO C ----------------");
                inputBoard.replace(movesToC.toArray(new Direction[movesToC.size()]), new Point(kx, ky));
                direction = movesToC.remove();
            } else {
                inputBoard.replace(moves.toArray(new Direction[moves.size()]), new Point(kx, ky));
                direction = moves.remove();
            }
            debug(inputBoard.toString());
            System.out.println(direction);
        }
    }

    // FUNCTIONS

    private static boolean computeHasReachedC(boolean hasReachedC, int kx, int ky) {
        if (hasReachedC) {
            return true;
        }
        return pointC != null && kx == pointC.x && ky == pointC.y;
    }

    public static BestMove floodFill(MatrixBooleanBoard walls, int kx, int ky) {
        MatrixBooleanBoard maze = walls.copy();
        BestMove bestMove = new BestMove();

        List<Point> edge = new ArrayList<>();
        Point currentPoint = new Point(kx, ky);
        edge.add(currentPoint);

        int currentDistance = 0;
        distanceBoard.set(currentPoint, currentDistance);
        while (edge.size() > 0) {
            currentDistance++;
            List<Point> toCheck = new ArrayList<>();
            for (Point p : edge) {
                for (Direction direction : Direction.values()) {
                    Point newP = p.add(direction);
                    if (!distanceBoard.isOutOfRange(newP) && !maze.isBlocked(newP)) {
                        maze.set(newP, false);
                        distanceBoard.set(newP, currentDistance);

                        int nbUnknownBlocks = inputBoard.computeNbUnknownBlocks(newP, direction);
                        BestMove move = new BestMove(newP, currentDistance, nbUnknownBlocks);
                        int compareResult = BestMove.COMPARATOR.compare(move, bestMove);
                        if (compareResult > 0) {
                            bestMove = move;
                        }

                        toCheck.add(newP);
                    }
                }
            }
            edge.clear();
            edge.addAll(toCheck);
        }
        return bestMove;
    }

    public static boolean hasFoundC() {
        return pointC != null;
    }

    public static Queue<Direction> pathFinding(int x, int y, Point pointToGo) {
        AntPathFinder pathFinder = new AntPathFinder();
        Direction[] path = pathFinder.findPath(
                wallsBoard,
                x, y,
                pointToGo.x, pointToGo.y
        );

        if (path != null) {
            Queue<Direction> moves = new LinkedList<>();
            Collections.addAll(moves, path);
            return moves;
        }
        return null;
    }

    public static void debug(String message) {
        if (DEBUG) {
            System.err.println(message);
        }
    }

    // CLASSES

    public enum Direction {
        UP(0, -1, 'U'),
        RIGHT(1, 0, 'R'),
        DOWN(0, 1, 'D'),
        LEFT(-1, 0, 'L');

        public int x;
        public int y;
        public char small;

        private Direction(int x, int y, char small) {
            this.x = x;
            this.y = y;
            this.small = small;
        }
    }

    public static class InputBoard extends Board {
        public char[][] map;

        public InputBoard(Scanner in, int width, int height, int kx, int ky, MatrixBooleanBoard availableBoard) {
            super(width, height);
            map = new char[width][height];
            for (int y = 0; y < height; y++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                char[] columns = ROW.toCharArray();
                for (int x = 0; x < width; x++) {
                    if (kx == x && ky == y) {
                        map[x][y] = '*';
                    } else {
                        map[x][y] = columns[x];
                    }
                    if (map[x][y] == 'C') {
                        pointC = new Point(x, y);
                    } else if (map[x][y] == 'T') {
                        pointT = new Point(x, y);
                    }
                    availableBoard.set(x, y, map[x][y] == '.' || map[x][y] == 'C' || map[x][y] == 'T' || map[x][y] == '*');
                }
                in.nextLine();
            }
        }

        public boolean isUnknown(Point p) {
            return map[p.x][p.y] == '?';
        }

        public int computeNbUnknownBlocks(Point p, Direction direction) {
            int nbUnknownBlocks = 0;
            List<Point> pointToCheckList = new ArrayList<>();
            pointToCheckList.add(p.add(direction));
            switch (direction) {
                case DOWN:
                case UP:
                    pointToCheckList.add(p.add(direction).add(Direction.LEFT));
                    pointToCheckList.add(p.add(direction).add(Direction.LEFT).add(Direction.LEFT));
                    pointToCheckList.add(p.add(direction).add(Direction.RIGHT));
                    pointToCheckList.add(p.add(direction).add(Direction.RIGHT).add(Direction.RIGHT));
                    break;
                case LEFT:
                case RIGHT:
                    pointToCheckList.add(p.add(direction).add(Direction.UP));
                    pointToCheckList.add(p.add(direction).add(Direction.UP).add(Direction.UP));
                    pointToCheckList.add(p.add(direction).add(Direction.DOWN));
                    pointToCheckList.add(p.add(direction).add(Direction.DOWN).add(Direction.DOWN));
                    break;
            }
            for (Point pointToCheck : pointToCheckList) {
                if (!distanceBoard.isOutOfRange(pointToCheck) && isUnknown(pointToCheck)) {
                    nbUnknownBlocks++;
                }
            }
            return nbUnknownBlocks;
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

        public void replace(Node moves) {
            Node current = moves;
            while (current != null) {
                if (map[current.p.x][current.p.y] != 'C') {
                    map[current.p.x][current.p.y] = current.direction.small;
                }
                current = current.next;
            }
        }

        private void replace(Direction[] moves, Point p) {
            Point currentPoint = p;
            for (Direction move : moves) {
                currentPoint = currentPoint.add(move);
                if (map[currentPoint.x][currentPoint.y] != 'C') {
                    map[currentPoint.x][currentPoint.y] = move.small;
                }
            }
        }
    }

    public static class MatrixBooleanBoard extends Board {
        private boolean[][] board;

        public MatrixBooleanBoard(int width, int height) {
            super(width, height);
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

        public boolean isBlocked(int x, int y) {
            return !get(x, y);
        }

        public boolean isBlocked(Point p) {
            return isBlocked(p.x, p.y);
        }

        public void setBlocked(int x, int y) {
            set(x, y, false);
        }

        public void setBlocked(Point p) {
            setBlocked(p.x, p.y);
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

    public static class DistanceBoard extends Board {
        private static final int INITIAL_VALUE = -1;
        private int[][] distance;

        public DistanceBoard(int width, int height) {
            super(width, height);
            this.distance = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    this.distance[x][y] = INITIAL_VALUE;
                }
            }
        }

        public int get(int x, int y) {
            return distance[x][y];
        }

        public int get(Point point) {
            return get(point.x, point.y);
        }

        public void set(Point point, int val) {
            distance[point.x][point.y] = val;
        }

        private boolean isOutOfRange(int x, int y) {
            return x < 0 || y < 0 || x > width - 1 || y > height - 1;
        }

        private boolean isOutOfRange(Point p) {
            return isOutOfRange(p.x, p.y);
        }

        @Override
        public String toString() {
            StringBuilder mapLine = new StringBuilder();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int val = get(x, y);
                    mapLine.append(val == 0 ? "*" : val == -1 ? "#" : val)
                            .append(" ");
                }
                mapLine.append('\n');
            }

            return mapLine.toString();
        }
    }

    public static class BestMove {
        public static final Comparator<BestMove> COMPARATOR = new Comparator<BestMove>() {
            @Override
            public int compare(BestMove o1, BestMove o2) {
                if (o1.nbUnknownBlocks == o2.nbUnknownBlocks) {
                    if (o1.distance == o2.distance) {
                        return 0;
                    }
                    return o1.distance > o2.distance ? -1 : 1;
                }
                return o1.nbUnknownBlocks > o2.nbUnknownBlocks ? 1 : -1;
            }
        };
        int x;
        int y;
        int distance = 0;
        int nbUnknownBlocks = 0;

        public BestMove() {
        }

        public BestMove(Point p, int distance, int nbUnknownBlocks) {
            this.x = p.x;
            this.y = p.y;
            this.distance = distance;
            this.nbUnknownBlocks = nbUnknownBlocks;
        }

        public String toString() {
            return "x = " + x + ", y = " + y + ", distance = " + distance + ", nbUnknownBlocks = " + nbUnknownBlocks;
        }
    }

    public static abstract class Board {
        protected int width;
        protected int height;

        public Board(int width, int height) {
            this.height = height;
            this.width = width;
        }
    }

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point add(int x, int y) {
            return new Point(this.x + x, this.y + y);
        }

        public Point add(Point pointToAdd) {
            return add(pointToAdd.x, pointToAdd.y);
        }

        public Point add(Direction direction) {
            return add(direction.x, direction.y);
        }

        @Override
        public String toString() {
            return "x = " + x + ", y = " + y;
        }
    }

    static class Node {
        Point p;
        Node next;
        Node previous;
        Direction direction;
        int length;

        public Node(Point p) {
            this.p = p;
            this.length = 1;
        }

        public Node(Point p, Direction direction) {
            this.p = p;
            this.direction = direction;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node current = this;
            while (current != null) {
                sb.append(current.direction).append("-");
                current = current.next;
            }
            return sb.toString();
        }
    }

    public static class AntPathFinder {
        // number of ants
        private static final int MAX_SIZE = 2;

        private MatrixBooleanBoard mygrid;
        private MatrixBooleanBoard doneGrid;

        public Direction[] findPath(MatrixBooleanBoard walls, int x1, int y1, int x2, int y2) {
            MatrixBooleanBoard grid = walls.copy();
            int size; //number of ants.
            Path ret = null;
            boolean done = false;
            List<AdamAnt> ants = new ArrayList<>(4);
            List<AdamAnt> babies = new ArrayList<>(100);

            if (hasProblem(grid, x1, y1, x2, y2)) {
                debug("has pb");
                return null;
            }

            doneGrid.setBlocked(x1, y1); //set source as done.

            if (x1 == x2 && y1 == y2) {
                return new Direction[0];
            } else {
                Direction[] order = makeOrder(x1, y1, x2, y2);
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
                    Direction[] order = makeOrder(aa.getX(), aa.getY(), x2, y2);

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

        private AdamAnt makeAnt(Direction direction, int x1, int y1, int x2, int y2) {
            return (new AdamAnt(x1, y1, direction, mygrid, doneGrid));
        }

        private Direction[] makeOrder(int x1, int y1, int x2, int y2) {
            Direction[] ret = new Direction[4];
            int xAdd = 0;
            int yAdd = 0;
            if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
                xAdd = 1;
            } else {
                yAdd = 1;
            }
            if (x2 > x1) {
                ret[1 - xAdd] = Direction.RIGHT;
                ret[2 + xAdd] = Direction.LEFT;
            } else {
                ret[1 - xAdd] = Direction.LEFT;
                ret[2 + xAdd] = Direction.RIGHT;
            }
            if (y2 > y1) {
                ret[1 - yAdd] = Direction.DOWN;
                ret[2 + yAdd] = Direction.UP;
            } else {
                ret[1 - yAdd] = Direction.UP;
                ret[2 + yAdd] = Direction.DOWN;
            }
            return ret;
        }

        public static class Path extends LinkedList<Direction> {

            public Path() {
                super();
            }

            public Path(List<Direction> list) {
                super(list);
            }

            public Direction[] getArray() {
                Direction[] ret = new Direction[super.size()];
                int i = 0;
                for (Iterator iter = super.iterator(); iter.hasNext(); i++) {
                    ret[i] = (Direction) iter.next();
                }
                return ret;
            }

            @Override
            public boolean add(Direction direction) {
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
            Direction dir;

            public AdamAnt(int x, int y, Direction d, MatrixBooleanBoard mygrid, MatrixBooleanBoard dgrid) {
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
                if (mygrid.isOutOfRange(i, j) || mygrid.isBlocked(i, j) || doneGrid.isBlocked(i, j)) {
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

            public void setDir(Direction d) {
                dir = d;
            }

            public Direction getDir() {
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
}
