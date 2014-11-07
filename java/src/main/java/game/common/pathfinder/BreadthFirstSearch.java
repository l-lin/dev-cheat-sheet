package game.common.pathfinder;

import java.util.LinkedList;
import java.util.Queue;

import game.common.board.MatrixBooleanBoard;
import game.common.command.FourDirection;
import game.common.entities.Point;

public class BreadthFirstSearch {
    public static Node bfs(MatrixBooleanBoard maze, Point start, Point end) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        Node current;
        // BreadthFirstSearch algorithm.
        while (!queue.isEmpty()) {
            current = queue.remove();
            for (FourDirection direction : FourDirection.values()) {
                Point p = current.p.add(direction);
                if (maze.isBlocked(p)) {
                    current.next = new Node(p, current.direction != null ? current.direction : direction);
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
        FourDirection direction;

        public Node(Point p) {
            this.p = p;
        }

        public Node(Point p, FourDirection direction) {
            this(p);
            this.direction = direction;
        }

        public Node(Node next, Node prev) {
            this.next = next;
            this.previous = prev;
        }
    }
}
