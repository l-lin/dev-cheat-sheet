package common;

public class Point {
    private int x;
    private int y;

    private Point() {
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point position) {
        this(position.x, position.y);
    }

    private Point add(Point pointToAdd) {
        return new Point(x + pointToAdd.x, y + pointToAdd.y);
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
