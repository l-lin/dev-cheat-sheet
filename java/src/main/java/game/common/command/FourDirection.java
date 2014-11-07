package game.common.command;

public enum FourDirection {
    N(0, -1, "S"),
    E(1, 0, "W"),
    S(0, 1, "N"),
    W(-1, 0, "E");

    public int x;
    public int y;
    private String opposite;

    private FourDirection(int x, int y, String opposite) {
        this.x = x;
        this.y = y;
        this.opposite = opposite;
    }

    public boolean isOppositeDirection(FourDirection direction) {
        return FourDirection.valueOf(this.opposite).equals(direction);
    }
}
