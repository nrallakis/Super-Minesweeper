package gr.nrallakis.superminesweeper.cell;

public class EmptyCell extends BoardCell {
    private int neighbourMines;
    public EmptyCell(int x, int y) {
        super(x, y);
    }

    public int getNeighbourMines() {
        return neighbourMines;
    }

    public void setNeighbourMines(int neighbourMines) {
        this.neighbourMines = neighbourMines;
    }
}
