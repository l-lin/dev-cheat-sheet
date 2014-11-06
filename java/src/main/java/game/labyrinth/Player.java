import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class Player {
    private static final boolean DEBUG = true;

    private static DistanceBoard distanceBoard;
    private static InputBoard inputBoard;
    private static DirectionBoard directionBoard;
    private static MatrixBooleanBoard availableBoard;
    private static Point pointC;
    private static Point pointT;
    private static boolean hasReachedC;

    public enum Direction {
        UP(0, -1, "U"),
        RIGHT(1, 0, "R"),
        DOWN(0, 1, "D"),
        LEFT(-1, 0, "L"),
        WAIT(0, 0, "-");

        public int x;
        public int y;
        public String small;

        private Direction(int x, int y, String small) {
            this.x = x;
            this.y = y;
            this.small = small;
        }
    }

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
            if (nbRounds > 100) {
                break;
            }

            int ky = in.nextInt(); // row where Kirk is located.
            int kx = in.nextInt(); // column where Kirk is located.
            in.nextLine();

            debug("R = " + R + " - C = " + C);
            debug("kx = " + kx + " - ky = " + ky);

            availableBoard = new MatrixBooleanBoard(R, C);
            inputBoard = new InputBoard(in, R, C, kx, ky, availableBoard);
            distanceBoard = new DistanceBoard(R, C);
            directionBoard = new DirectionBoard(R, C);
            hasReachedC = computeHasReachedC(hasReachedC, kx, ky);

            BestMove bestMove;
            bestMove = floodFill(kx, ky);
            if (hasReachedC) {
                // Going back to T
                bestMove = pathFinding(kx, ky, pointT);
            } else {
                if (hasFoundC()) {
                    // Going to C
                    bestMove = pathFinding(kx, ky, pointC);
                }
            }

            debug(inputBoard.toString());
            debug(distanceBoard.toString());
            debug(directionBoard.toString());
            debug(bestMove.toString());
            System.out.println(directionBoard.get(bestMove.x, bestMove.y));
        }
    }

    private static boolean computeHasReachedC(boolean hasReachedC, int kx, int ky) {
        if (hasReachedC) {
            return true;
        }
        return pointC != null && kx == pointC.x && ky == pointC.y;
    }

    public static BestMove floodFill(int x, int y) {
        BestMove bestMove = new BestMove();
        List<Point> edge = new ArrayList<>();
        Point currentPoint = new Point(x, y);
        edge.add(currentPoint);

        int currentDistance = 0;
        distanceBoard.set(currentPoint, currentDistance);
        while (edge.size() > 0) {
            currentDistance++;
            List<Point> toCheck = new ArrayList<>();
            for (Point p : edge) {
                for (Direction direction : Direction.values()) {
                    Point newP = p.add(direction);
                    if (distanceBoard.isOutOfRange(newP) ||
                            distanceBoard.isVisited(newP) ||
                            inputBoard.isBlocked(newP) ||
                            inputBoard.isUnknown(newP)) {
                        continue;
                    }
                    distanceBoard.set(newP, currentDistance);
                    directionBoard.set(newP, direction);

                    int nbUnknownBlocks = inputBoard.computeNbUnknownBlocks(newP, direction);
                    BestMove move = new BestMove(newP, currentDistance, nbUnknownBlocks);
                    int compareResult = BestMove.COMPARATOR.compare(move, bestMove);
                    if (compareResult > 0) {
                        bestMove = move;
                    }

                    toCheck.add(newP);
                }
            }
            edge.clear();
            edge.addAll(toCheck);
        }
        return bestMove;
    }

    public static BestMove pathFinding(int x, int y, Point pointToGo) {
        Node node = bfs(availableBoard, new Point(x, y), pointToGo);
        return new BestMove(node.p.x, node.p.y);
    }

    public static boolean hasFoundC() {
        return pointC != null;
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

        public boolean isBlocked(Point p){
            return map[p.x][p.y] == '#';
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
                    int val = get(x, y);
                    mapLine.append(val == 0 ? "*" : val == -1 ? "#" : val)
                            .append(" ");
                }
                mapLine.append('\n');
            }

            return mapLine.toString();
        }
    }

    public static class DirectionBoard extends Board {
        private Direction[][] directions;
        public DirectionBoard(int nbRows, int nbColumns) {
            super(nbRows, nbColumns);
            this.directions = new Direction[nbColumns][nbRows];
            for (int x = 0; x < nbColumns; x++) {
                for (int y = 0; y < nbRows; y++) {
                    this.directions[x][y] = Direction.WAIT;
                }
            }
        }

        public Direction get(int x, int y) {
            return directions[x][y];
        }

        public void set(Point p, Direction value) {
            directions[p.x][p.y] = value;
        }

        @Override
        public String toString() {
            StringBuilder mapLine = new StringBuilder();
            for (int y = 0; y < nbRows; y++) {
                for (int x = 0; x < nbColumns; x++) {
                    mapLine.append(get(x, y).small).append(" ");
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
                    return o1.distance > o2.distance ? 1 : -1;
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
        public BestMove(int x, int y) {
            this.x = x;
            this.y = y;
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

        public Point(Point position) {
            this(position.x, position.y);
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

        public void set(Point newPosition) {
            x = newPosition.x;
            y = newPosition.y;
        }

        public Point copy() {
            return new Point(x, y);
        }

        @Override
        public String toString() {
            return "x = " + x + ", y = " + y;
        }
    }

    public static Node bfs(MatrixBooleanBoard maze, Point start, Point end) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        Node current;
        // BreadthFirstSearch algorithm.
        while (!queue.isEmpty()) {
            current = queue.remove();
            for (Direction direction : Direction.values()) {
                Point p = current.p.add(direction);
                if (maze.isBlocked(p)) {
                    current.next = new Node(p);
                    current.next.previous = current;
                    queue.add(current.next);
                    maze.set(p, false);
                }
            }
            if (current.p.x == end.x && current.p.y == end.y) {
                return current;
            }

        }
        // FAILLLLL!!!! t(-_-t)
        return null;
    }

    static class Node {
        Point p;
        Node next;
        Node previous;

        public Node(Point p) {
            this.p = p;
        }

        public Node(Node next, Node prev) {
            this.next = next;
            this.previous = prev;
        }
    }

    public static void debug(String message) {
        if (DEBUG) {
            System.err.println(message);
        }
    }
}
