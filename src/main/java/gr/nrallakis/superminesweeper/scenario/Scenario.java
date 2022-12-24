package gr.nrallakis.superminesweeper.scenario;

import static gr.nrallakis.superminesweeper.utils.RangeUtils.inRange;

public class Scenario {
    private final int minesCount;
    private final int totalTime;
    private final boolean hasSuperMine;
    private final ScenarioRules rules;

    public Scenario(int minesCount, int totalTime, boolean hasSuperMine, ScenarioRules rules) {
        this.minesCount = minesCount;
        this.totalTime = totalTime;
        this.hasSuperMine = hasSuperMine;
        this.rules = rules;
    }

    public boolean isValid() {
        boolean superMineNotAllowed = !getRules().isSuperMineAllowed();
        if (superMineNotAllowed && hasSuperMine()) return false;

        return inRange(getMinesCount(), getRules().getMinMinesCount(), getRules().getMaxMinesCount())
                && inRange(getTotalTime(), getRules().getMinTotalTime(), getRules().getMaxTotalTime());
    }

    public int getMinesCount() {
        return minesCount;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean hasSuperMine() {
        return hasSuperMine;
    }

    public ScenarioRules getRules() {
        return rules;
    }
}


