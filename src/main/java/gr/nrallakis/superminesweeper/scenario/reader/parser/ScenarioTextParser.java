package gr.nrallakis.superminesweeper.scenario.reader.parser;

import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidValueException;
import gr.nrallakis.superminesweeper.scenario.reader.parser.validations.ScenarioValidator;

public class ScenarioTextParser implements ScenarioParser {
    @Override
    public Scenario parse(String scenarioText) throws InvalidValueException, InvalidDescriptionException {
        String[] lines = scenarioText.split("\n");
        if (lines.length != 4)
            throw new InvalidDescriptionException();

        int difficulty, minesCount, totalTime;
        boolean isSuperMine;
        try {
            difficulty = Integer.parseInt(lines[0]);
            minesCount = Integer.parseInt(lines[1]);
            totalTime = Integer.parseInt(lines[2]);
            isSuperMine = Integer.parseInt(lines[3]) != 0;
        } catch (NumberFormatException e) {
            throw new InvalidValueException();
        }

        int boardSize = getBoardSizeForDifficulty(difficulty);
        var scenario = new Scenario(difficulty, boardSize, minesCount, totalTime, isSuperMine);

        ScenarioValidator validator = new ScenarioValidator();
        if (validator.isValid(scenario)) {
            return scenario;
        } else {
            throw new InvalidValueException();
        }
    }

    private int getBoardSizeForDifficulty(int difficulty) throws InvalidValueException {
        if (difficulty == 1) {
            return 9;
        } else if (difficulty == 2) {
            return 16;
        }
        throw new InvalidValueException();
    }
}
