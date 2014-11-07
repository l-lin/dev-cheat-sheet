import java.util.*;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class Player {
    private static final boolean DEBUG = true;

    private static DistanceBoard distanceBoard;
    private static InputBoard inputBoard;
    private static MatrixBooleanBoard wallsBoard;
    private static Point pointC;
    private static Point pointT;
    private static boolean hasReachedC;
    private static Node moves;
    private static Node movesToC;
    private static Node movesToT;

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

            wallsBoard = new MatrixBooleanBoard(R, C);
            inputBoard = new InputBoard(in, R, C, kx, ky, wallsBoard);
            distanceBoard = new DistanceBoard(R, C);
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
                    if (moves == null) {
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
                inputBoard.replace(movesToT);
                direction = movesToT.direction;
                movesToT = movesToT.next;
            } else if (movesToC != null) {
                debug("---------------- GOING TO C ----------------");
                inputBoard.replace(movesToC);
                direction = movesToC.direction;
                movesToC = movesToC.next;
            }  else {
                inputBoard.replace(moves);
                direction = moves.direction;
                moves = moves.next;
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

    public static Node pathFinding(int x, int y, Point pointToGo) {
        return bfs(wallsBoard, new Point(x, y), pointToGo);
    }

    public static boolean hasFoundC() {
        return pointC != null;
    }

    public static Node bfs(MatrixBooleanBoard walls, Point start, Point end) {
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

            Stack<Node> nodeStacks = new Stack<>();
            while(!goal.p.equals(start)) {
                if (goal.direction != null) {
                    nodeStacks.add(goal);
                }
                goal = goal.previous;
            }
            Node finalNode = nodeStacks.pop();
            Node tmp = finalNode;
            while(!nodeStacks.isEmpty()) {
                Node next = nodeStacks.pop();
                next.previous = tmp;
                tmp.next = next;
                tmp = tmp.next;
            }
            return finalNode;
        }

        // FAILLLLL!!!! t(-_-t)
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
        public InputBoard(Scanner in, int nbRows, int nbColumns, int kx, int ky, MatrixBooleanBoard availableBoard) {
            super(nbRows, nbColumns);
            map = new char[nbColumns][nbRows];
            for (int y = 0; y < nbRows; y++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                char[] columns = ROW.toCharArray();
                for (int x = 0; x < nbColumns; x++) {
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
                    availableBoard.set(x, y, map[x][y] == '.' || map[x][y] == 'C' || map[x][y] == 'T');
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
            for (int y = 0; y < nbRows; y++) {
                for (int x = 0; x < nbColumns; x++) {
                    sb.append(map[x][y]).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        public void replace(Node moves) {
            Node current = moves;
            while(current != null) {
                if (map[current.p.x][current.p.y] != 'C') {
                    map[current.p.x][current.p.y] = current.direction.small;
                }
                current = current.next;
            }
        }
    }

    public static class MatrixBooleanBoard extends Board {
        private boolean[][] board;

        public MatrixBooleanBoard(int nbRows, int nbColumns) {
            super(nbRows, nbColumns);
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
            return !get(x, y);
        }

        public boolean isBlocked(Point p) {
            return isBlocked(p.x, p.y);
        }

        public MatrixBooleanBoard copy() {
            MatrixBooleanBoard clone = new MatrixBooleanBoard(nbRows, nbColumns);
            for (int x = 0; x < nbColumns; x++) {
                System.arraycopy(board[x], 0, clone.board[x], 0, nbRows);
            }

            return clone;
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

    public static class DistanceBoard extends Board {
        private static final int INITIAL_VALUE = -1;
        private int[][] distance;

        public DistanceBoard(int nbRows, int nbColumns) {
            super(nbRows, nbColumns);
            this.distance = new int[nbColumns][nbRows];
            for (int x = 0; x < nbColumns; x++) {
                for (int y = 0; y < nbRows; y++) {
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
            return x < 0 || y < 0 || x > nbColumns - 1 || y > nbRows - 1;
        }

        private boolean isOutOfRange(Point p) {
            return isOutOfRange(p.x, p.y);
        }

        @Override
        public String toString() {
            StringBuilder mapLine = new StringBuilder();

            for (int y = 0; y < nbRows; y++) {
                for (int x = 0; x < nbColumns; x++) {
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
        protected int nbColumns;
        protected int nbRows;
        public Board(int nbRows, int nbColumns) {
            this.nbRows = nbRows;
            this.nbColumns = nbColumns;
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
            while(current != null) {
                sb.append(current.direction).append("-");
                current = current.next;
            }
            return sb.toString();
        }
    }
}
