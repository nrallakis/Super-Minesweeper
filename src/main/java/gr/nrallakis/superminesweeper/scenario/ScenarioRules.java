package gr.nrallakis.superminesweeper.scenario;

public class ScenarioRules {
    public final int boardSize;
    public final int difficulty;
    public final int minMinesCount;
    public final int maxMinesCount;
    public final int minTotalTime;
    public final int maxTotalTime;
    public boolean isSuperMineAllowed;

    public ScenarioRules(int difficulty, int boardSize, int minMinesCount, int maxMinesCount, int minTotalTime, int maxTotalTime, boolean isSuperMineAllowed) {
        this.difficulty = difficulty;
        this.boardSize = boardSize;
        this.minMinesCount = minMinesCount;
        this.maxMinesCount = maxMinesCount;
        this.minTotalTime = minTotalTime;
        this.maxTotalTime = maxTotalTime;
        this.isSuperMineAllowed = isSuperMineAllowed;
    }
}
