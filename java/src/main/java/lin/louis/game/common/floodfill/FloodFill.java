package lin.louis.game.common.floodfill;

import java.util.ArrayList;
import java.util.List;

import lin.louis.game.common.board.MatrixBooleanBoard;
import lin.louis.game.common.board.MatrixIntegerBoard;
import lin.louis.game.common.command.FourDirection;
import lin.louis.game.common.Point;

public class FloodFill {
    public static MatrixIntegerBoard floodFill(MatrixBooleanBoard walls, Point start) {
        MatrixIntegerBoard distanceBoard = new MatrixIntegerBoard(walls.width, walls.height);
        MatrixBooleanBoard maze = walls.copy();

        List<Point> edge = new ArrayList<>();
        edge.add(start);

        int currentDistance = 0;
        distanceBoard.set(start, currentDistance);
        maze.set(start, false);
        while (edge.size() > 0) {
            currentDistance++;
            List<Point> toCheck = new ArrayList<>();
            for (Point p : edge) {
                for (FourDirection direction : FourDirection.values()) {
                    Point newP = p.add(direction);
                    if (!distanceBoard.isOutOfRange(newP) && !maze.isBlocked(newP)) {
                        maze.set(newP, false);
                        distanceBoard.set(newP, currentDistance);

                        toCheck.add(newP);
                    }
                }
            }
            edge.clear();
            edge.addAll(toCheck);
        }
        return distanceBoard;
    }
}
