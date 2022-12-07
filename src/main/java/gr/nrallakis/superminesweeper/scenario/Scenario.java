package gr.nrallakis.superminesweeper.scenario;

import static gr.nrallakis.superminesweeper.utils.RangeUtils.inRange;

public class Scenario {
    public final int minesCount;
    public final int totalTime;
    public final boolean hasSuperMine;
    public final ScenarioRules rules;

    public Scenario(int minesCount, int totalTime, boolean hasSuperMine, ScenarioRules rules) {
        this.minesCount = minesCount;
        this.totalTime = totalTime;
        this.hasSuperMine = hasSuperMine;
        this.rules = rules;
    }

    public boolean isValid() {
        boolean superMineNotAllowed = !rules.isSuperMineAllowed;
        if (superMineNotAllowed && hasSuperMine) return false;

        return inRange(minesCount, rules.minMinesCount, rules.maxMinesCount)
                && inRange(totalTime, rules.minTotalTime, rules.maxTotalTime);
    }
}


