package gr.nrallakis.superminesweeper.scenario;

public abstract class Scenario {
    public final int boardSize;
    public final int minesCount;
    public final int totalTime;
    public final boolean hasSuperMine;

    public Scenario(int boardSize, int minesCount, int totalTime, boolean hasSuperMine) {
        this.boardSize = boardSize;
        this.minesCount = minesCount;
        this.totalTime = totalTime;
        this.hasSuperMine = hasSuperMine;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "boardSize=" + boardSize +
                ", minesCount=" + minesCount +
                ", totalTime=" + totalTime +
                ", hasSuperMine=" + hasSuperMine +
                '}';
    }

    public abstract boolean isValid();
}


