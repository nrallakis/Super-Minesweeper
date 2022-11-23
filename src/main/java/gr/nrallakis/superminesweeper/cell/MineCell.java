package gr.nrallakis.superminesweeper.cell;

public class MineCell extends BoardCell {
    public boolean isSuper;
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
        return x + "," + y + "," + (isSuper ? 1 : 0);
    }
}
