package game.common.command;

public enum FourDirection {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0);

    public int x;
    public int y;

    private FourDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
