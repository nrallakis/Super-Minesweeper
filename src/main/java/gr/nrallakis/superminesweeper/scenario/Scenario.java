package gr.nrallakis.superminesweeper.scenario;

public class Scenario {
    public final int difficulty;
    public final int boardSize;
    public final int minesCount;
    public final int totalTime;
    public final boolean hasSuperMine;

    public Scenario(int difficulty, int boardSize, int minesCount, int totalTime, boolean hasSuperMine) {
        this.difficulty = difficulty;
        this.boardSize = boardSize;
        this.minesCount = minesCount;
        this.totalTime = totalTime;
        this.hasSuperMine = hasSuperMine;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "difficulty=" + difficulty +
                ", boardSize=" + boardSize +
                ", minesCount=" + minesCount +
                ", totalTime=" + totalTime +
                ", hasSuperMine=" + hasSuperMine +
                '}';
    }
}


