package gr.nrallakis.superminesweeper.scenario;

import static gr.nrallakis.superminesweeper.utils.RangeUtils.inRange;

public class EasyScenario extends Scenario {
    public EasyScenario(int minesCount, int totalTime) {
        super(9, minesCount, totalTime, false);
    }

    @Override
    public boolean isValid() {
        return inRange(minesCount, 9, 11)
                && inRange(totalTime, 120, 180)
                && !hasSuperMine;
    }
}
