package gr.nrallakis.superminesweeper.cell;

public class MineCell extends BoardCell {
    public boolean isSuper;

    public MineCell(int x, int y) {
        super(x, y);
        this.isSuper = false;
    }

    public MineCell(int x, int y, boolean isSuper) {
        super(x, y);
        this.isSuper = isSuper;
    }

    public String toText() {
        return x + "," + y + "," + (isSuper ? 1 : 0);
    }
}
