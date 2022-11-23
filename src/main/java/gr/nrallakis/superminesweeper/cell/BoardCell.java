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

    public boolean isNotRevealed() { return !isRevealed(); }

    public void reveal() {
        isMarkedAsMine = false;
        isRevealed = true;
    }

    public boolean isMarkedAsMine() {
        return isMarkedAsMine;
    }

    public void setMarkedAsMine(boolean markedAsMine) {
        if (isRevealed)
            isMarkedAsMine = false;
        else
            isMarkedAsMine = markedAsMine;
    }
}

