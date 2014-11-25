import java.util.*;

class LabyrinthPlayer {
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
    private static Queue<Point> fourLastPosition = new LinkedList<>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // number of rows.
        int C = in.nextInt(); // number of columns.
        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        in.nextLine();

        int nbRounds = 1;

        // game loop
        while (true) {
            nbRounds++;

            int ky = in.nextInt(); // row where Kirk is located.
            int kx = in.nextInt(); // column where Kirk is located.
            in.nextLine();

            Point kirkPos = new Point(kx, ky);
            debug("R = " + R + " - C = " + C + " - A = " + A);
            debug("Kirk position = " + kirkPos.toString());

            if (fourLastPosition.size() == 4) {
                fourLastPosition.remove();
            }
            fourLastPosition.add(kirkPos);

            wallsBoard = new MatrixBooleanBoard(C, R);
            inputBoard = new InputBoard(in, C, R, kirkPos, wallsBoard);
            distanceBoard = new DistanceBoard(C, R);
            hasReachedC = computeHasReachedC(hasReachedC, kirkPos);

            if (hasReachedC && movesToT == null) {
                // Going back to T
                movesToT = pathFinding(kirkPos, pointT);
            } else {
                if (hasFoundC() && movesToC == null && (isGoingBackOrForth(fourLastPosition) || (nbRounds > 1000))) {
                    // Going to C
                    movesToC = pathFinding(kirkPos, pointC);
                }
                if (movesToC == null) {
//                    if (moves == null || moves.isEmpty()) {
                        BestMove bestMove = floodFill(wallsBoard, kirkPos);
                        debug("BestMove = " + bestMove.toString());
                        moves = pathFinding(kirkPos, new Point(bestMove.x, bestMove.y));
//                        debug(distanceBoard.toString());
//                    }
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

    private static boolean isGoingBackOrForth(Queue<Point> fourLastPosition) {
        if (fourLastPosition.size() < 4) {
            return false;
        }
        Queue<Point> tmp = new LinkedList<>(fourLastPosition);
        int i = 0;
        for (Point p : tmp) {
            debug("fourLastPosition.get(" + i + ") = " + p);
        }
        Point p0 = tmp.remove();
        Point p1 = tmp.remove();
        Point p2 = tmp.remove();
        Point p3 = tmp.remove();
        return p0.equals(p2) && p1.equals(p3);
    }

    // FUNCTIONS

    private static boolean computeHasReachedC(boolean hasReachedC, Point kirkPos) {
        if (hasReachedC) {
            return true;
        }
        return pointC != null && kirkPos.equals(pointC);
    }

    public static BestMove floodFill(MatrixBooleanBoard walls, Point currentPoint) {
        MatrixBooleanBoard maze = walls.copy();
        BestMove bestMove = new BestMove();

        List<Point> edge = new ArrayList<>();
        edge.add(currentPoint);

        int currentDistance = 0;
        distanceBoard.set(currentPoint, currentDistance);
        maze.set(currentPoint, false);
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
                        } else if (compareResult == 0) {
                            bestMove = inputBoard.whichOneIsTheBest(bestMove, move);
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

    public static Queue<Direction> pathFinding(Point start, Point goal) {
        Direction[] path = bfs(wallsBoard, start, goal);
//        AntPathFinder pathFinder = new AntPathFinder();
//        Direction[] path = pathFinder.findPath(wallsBoard, start, goal);

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

        public InputBoard(Scanner in, int width, int height, Point kirkPos, MatrixBooleanBoard availableBoard) {
            super(width, height);
            map = new char[width][height];
            for (int y = 0; y < height; y++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                char[] columns = ROW.toCharArray();
                for (int x = 0; x < width; x++) {
                    if (kirkPos.x == x && kirkPos.y == y) {
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
            return isUnknown(p.x, p.y);
        }

        public boolean isUnknown(int x, int y) {
            return map[x][y] == '?';
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

        public BestMove whichOneIsTheBest(BestMove bm1, BestMove bm2) {
            if (bm1.equals(bm2)) {
                return bm1;
            }
            int xStart1;
            int xEnd1;
            int yStart1;
            int yEnd1;
            int xStart2;
            int xEnd2;
            int yStart2;
            int yEnd2;
            if (bm1.x == bm2.x) {
                xStart1 = 0;
                xEnd1 = width;
                yStart1 = bm1.y < bm2.y ? 0 : bm1.y;
                yEnd1 = bm1.y < bm2.y ? bm1.y : height;
                xStart2 = 0;
                xEnd2 = width;
                yStart2 = bm2.y < bm1.y ? 0 : bm2.y;
                yEnd2 = bm2.y < bm1.y ? bm2.y : height;
            } else if (bm1.y == bm2.y) {
                xStart1 = bm1.x < bm2.x ? 0 : bm1.x;
                yStart1 = 0;
                xEnd1 = bm1.x < bm2.x ? bm1.x : width;
                yEnd1 = height;
                xStart2 = bm2.x < bm1.x ? 0 : bm2.x;
                yStart2 = 0;
                xEnd2 = bm2.x < bm1.x ? bm2.x : width;
                yEnd2 = height;
            } else {
                xStart1 = bm1.x < bm2.x ? 0 : bm1.x;
                yStart1 = bm1.y < bm2.y ? 0 : bm1.y;
                xEnd1 = bm1.x < bm2.x ? bm1.x : width;
                yEnd1 = bm1.y < bm2.y ? bm1.y : height;
                xStart2 = bm2.x < bm1.x ? 0 : bm2.x;
                yStart2 = bm2.y < bm1.y ? 0 : bm2.y;
                xEnd2 = bm2.x < bm1.x ? bm2.x : width;
                yEnd2 = bm2.y < bm1.y ? bm2.y : height;
            }

            int nb1 = 0;
            for (int x = xStart1; x < xEnd1; x++) {
                for (int y = yStart1; y < yEnd1; y++) {
                    if (!distanceBoard.isOutOfRange(x, y) && isUnknown(x, y)) {
                        nb1++;
                    }
                }
            }
            int nb2 = 0;
            for (int x = xStart2; x < xEnd2; x++) {
                for (int y = yStart2; y < yEnd2; y++) {
                    if (!distanceBoard.isOutOfRange(x, y) && isUnknown(x, y)) {
                        nb2++;
                    }
                }
            }
            return nb1 > nb2 ? bm1 : bm2;
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
                if (o1.distance == 0) {
                    return -1;
                }
                if (o2.distance == 0) {
                    return 1;
                }
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

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Point)) {
                return false;
            }
            Point p = (Point) obj;
            return this.x == p.x && this.y == p.y;
        }
    }

    public static Direction[] bfs(MatrixBooleanBoard walls, Point start, Point end) {
        MatrixBooleanBoard maze = walls.copy();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        Node current;
        List<Node> goalList = new ArrayList<>();
        while (!queue.isEmpty()) {
            current = queue.remove();
            for (Direction direction : Direction.values()) {
                Point p = current.p.add(direction);
                if (!distanceBoard.isOutOfRange(p) && !maze.isBlocked(p)) {
                    current.next = new Node(p, direction);
                    current.next.previous = current;
                    current.next.length = current.length + 1;

                    queue.add(current.next);
                    maze.set(p, false);
                }
            }
            if (current.p.x == end.x && current.p.y == end.y) {
                goalList.add(current);
            }
        }

        if (!goalList.isEmpty()) {
            Node goal = goalList.get(0);
            for (Node possibleGoal : goalList) {
                if (possibleGoal.length < goal.length) {
                    goal = possibleGoal;
                }
            }

            int size = 0;
            Stack<Node> nodeStacks = new Stack<>();
            while(!goal.p.equals(start)) {
                size++;
                if (goal.direction != null) {
                    nodeStacks.add(goal);
                }
                goal = goal.previous;
            }
            Direction[] moves = new Direction[size];
            int index = 0;
            while(!nodeStacks.isEmpty()) {
                Node next = nodeStacks.pop();
                moves[index] = next.direction;
                index++;
            }
            return moves;
        }

        // FAILLLLL!!!! t(-_-t)
        return null;
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
            while(current != null) {
                sb.append(current.direction).append("-");
                current = current.next;
            }
            return sb.toString();
        }
    }

    // ----------------------------------------
    // PATH FINDER
    // ----------------------------------------

    public static class AntPathFinder {
        private static final int MAX_SIZE = 1;
        private MatrixBooleanBoard walls;
        private MatrixBooleanBoard doneGrid;

        public Direction[] findPath(MatrixBooleanBoard walls, Point start, Point end) {
            init(walls);

            int size; //number of ants.
            List<Path> retList = new ArrayList<>();
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
                return new Direction[0];
            } else {
                Direction[] order = makeOrder(start, end);
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
                    Direction[] order = makeOrder(aa.p, end);

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
                        retList.add(aa.getPath());
                        break;
                    }
                }
            }

            if (!retList.isEmpty()) {
                Path goal = retList.get(0);
                for (Path ret : retList) {
                    if (ret.getArray().length < goal.getArray().length) {
                        goal = ret;
                    }
                }
                return goal.getArray();
            }
            return null;
        }

        private void init(MatrixBooleanBoard grid) {
            walls = grid.copy();
            doneGrid = new MatrixBooleanBoard(walls.width, walls.height);
            doneGrid.initBoard(true);
        }

        private AdamAnt makeAnt(Direction direction, Point p) {
            return (new AdamAnt(p, direction, walls, doneGrid));
        }

        private Direction[] makeOrder(Point p1, Point p2) {
            Direction[] ret = new Direction[4];
            int xAdd = 0;
            int yAdd = 0;
            if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
                xAdd = 1;
            } else {
                yAdd = 1;
            }
            if (p2.x > p1.x) {
                ret[1 - xAdd] = Direction.RIGHT;
                ret[2 + xAdd] = Direction.LEFT;
            } else {
                ret[1 - xAdd] = Direction.LEFT;
                ret[2 + xAdd] = Direction.RIGHT;
            }
            if (p2.y > p1.y) {
                ret[1 - yAdd] = Direction.DOWN;
                ret[2 + yAdd] = Direction.UP;
            } else {
                ret[1 - yAdd] = Direction.DOWN;
                ret[2 + yAdd] = Direction.UP;
            }
            return ret;
        }

        public static class Path extends LinkedList<Direction> {

            public Path() {
                super();
            }

            public Direction[] getArray() {
                Direction[] ret = new Direction[super.size()];
                int i = 0;
                for (Iterator it = super.iterator(); it.hasNext(); i++) {
                    ret[i] = (Direction) it.next();
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
            private static MatrixBooleanBoard walls;
            private static MatrixBooleanBoard doneGrid;
            Point p;
            Direction dir;

            public AdamAnt(Point p, Direction d, MatrixBooleanBoard walls, MatrixBooleanBoard dgrid) {
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

            public void setDir(Direction d) {
                dir = d;
            }

            public Direction getDir() {
                return dir;
            }

            public Path getPath() {
                return this;
            }
        }
    }
}
