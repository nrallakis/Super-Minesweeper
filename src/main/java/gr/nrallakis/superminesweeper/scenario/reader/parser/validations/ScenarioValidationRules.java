package gr.nrallakis.superminesweeper.scenario.reader.parser.validations;

import gr.nrallakis.superminesweeper.scenario.Scenario;

public class ScenarioValidationRules {
    final int minMines;
    final int maxMines;
    final int minTime;
    final int maxTime;
    final boolean superMinesAllowed;

    public ScenarioValidationRules(int minMines, int maxMines, int minTime, int maxTime, boolean superMinesAllowed) {
        this.minMines = minMines;
        this.maxMines = maxMines;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.superMinesAllowed = superMinesAllowed;
    }

    boolean isValid(Scenario scenario) {
        if (scenario.minesCount < minMines || scenario.minesCount > maxMines)
            return false;
        if (scenario.totalTime < minTime || scenario.totalTime > maxTime)
            return false;
        if (scenario.hasSuperMine && !superMinesAllowed)
            return false;
        return true;
    }
}
