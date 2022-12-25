package gr.nrallakis.superminesweeper.game.cell;

public class MineCell extends BoardCell {
    private final boolean isSuper;
    private boolean isRevealedByUser;

    public MineCell(int x, int y) {
        super(x, y);
        this.isSuper = false;
        this.isRevealedByUser = false;
    }

    public MineCell(int x, int y, boolean isSuper) {
        super(x, y);
        this.isSuper = isSuper;
    }

    public boolean isRevealedByUser() {
        return isRevealedByUser;
    }

    public void revealedByUser() {
        isRevealedByUser = true;
    }

    public String toText() {
        return getX() + "," + getY() + "," + (isSuper() ? 1 : 0);
    }

    public boolean isSuper() {
        return isSuper;
    }
}
