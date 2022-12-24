package gr.nrallakis.superminesweeper.scenario.parser;

import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.ScenarioFactory;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidValueException;

public class ScenarioTextParser implements ScenarioParser {
    @Override
    public Scenario parse(String scenarioText) throws InvalidValueException, InvalidDescriptionException {
        String[] lines = scenarioText.split("\n");
        if (lines.length != 4)
            throw new InvalidDescriptionException();

        int difficulty, minesCount, totalTime;
        boolean hasSuperMine;
        try {
            difficulty = Integer.parseInt(lines[0]);
            minesCount = Integer.parseInt(lines[1]);
            totalTime = Integer.parseInt(lines[2]);
            hasSuperMine = Integer.parseInt(lines[3]) != 0;
        } catch (NumberFormatException e) {
            throw new InvalidValueException();
        }

        var scenarioFactory = new ScenarioFactory();
        var scenario = scenarioFactory.buildScenario(difficulty, minesCount, totalTime, hasSuperMine);
        if (scenario.isValid()) {
            return scenario;
        } else {
            throw new InvalidValueException();
        }
    }
}
