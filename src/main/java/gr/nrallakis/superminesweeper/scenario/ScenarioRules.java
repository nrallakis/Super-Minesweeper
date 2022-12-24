package gr.nrallakis.superminesweeper.scenario;

public class ScenarioRules {
    private final int boardSize;
    private final int difficulty;
    private final int minMinesCount;
    private final int maxMinesCount;
    private final int minTotalTime;
    private final int maxTotalTime;
    private final boolean isSuperMineAllowed;

    public ScenarioRules(int difficulty, int boardSize, int minMinesCount, int maxMinesCount, int minTotalTime, int maxTotalTime, boolean isSuperMineAllowed) {
        this.difficulty = difficulty;
        this.boardSize = boardSize;
        this.minMinesCount = minMinesCount;
        this.maxMinesCount = maxMinesCount;
        this.minTotalTime = minTotalTime;
        this.maxTotalTime = maxTotalTime;
        this.isSuperMineAllowed = isSuperMineAllowed;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMinMinesCount() {
        return minMinesCount;
    }

    public int getMaxMinesCount() {
        return maxMinesCount;
    }

    public int getMinTotalTime() {
        return minTotalTime;
    }

    public int getMaxTotalTime() {
        return maxTotalTime;
    }

    public boolean isSuperMineAllowed() {
        return isSuperMineAllowed;
    }
}
