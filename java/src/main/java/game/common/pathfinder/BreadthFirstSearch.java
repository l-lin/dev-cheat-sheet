package game.common.pathfinder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import game.common.board.MatrixBooleanBoard;
import game.common.command.FourDirection;
import game.common.entities.Point;

public class BreadthFirstSearch {
    public static Node bfs(MatrixBooleanBoard walls, Point start, Point end) {
        MatrixBooleanBoard maze = walls.copy();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start));
        Node current;
        List<Node> goalList = new ArrayList<>();
        while (!queue.isEmpty()) {
            current = queue.remove();
            for (FourDirection direction : FourDirection.values()) {
                Point p = current.p.add(direction);
                if (!maze.isOutOfRange(p) && !maze.isBlocked(p)) {
//                    current.next = new Node(p, current.direction != null ? current.direction : direction);
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

    public static class Node {
        public Point p;
        public Node next;
        public Node previous;
        public FourDirection direction;
        public int length;

        public Node(Point p) {
            this.p = p;
            this.length = 1;
        }

        public Node(Point p, FourDirection direction) {
            this(p);
            this.direction = direction;
        }

        @Override
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
