package gr.nrallakis.superminesweeper.scenario;

import static gr.nrallakis.superminesweeper.utils.RangeUtils.inRange;

public class HardScenario extends Scenario {
    public HardScenario(int minesCount, int totalTime, boolean hasSuperMine) {
        super(16, minesCount, totalTime, hasSuperMine);
    }

    @Override
    public boolean isValid() {
        return inRange(minesCount, 35, 45)
                && inRange(totalTime, 240, 360);
    }
}
