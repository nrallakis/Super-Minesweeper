package gr.nrallakis.superminesweeper.cell;

public class BoardCell {
    public final int x;
    public final int y;
    private boolean isRevealed;
    private boolean isMarkedAsMine;

    public BoardCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isRevealed = false;
        this.isMarkedAsMine = false;
    }


    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isMarkedAsMine() {
        return isMarkedAsMine;
    }

    public void setMarkedAsMine(boolean markedAsMine) {
        isMarkedAsMine = markedAsMine;
    }
}

