package gr.nrallakis.superminesweeper.game.scenario.parser;


import gr.nrallakis.superminesweeper.game.scenario.Scenario;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidValueException;

public interface ScenarioParser {
    Scenario parse(String input) throws InvalidValueException, InvalidDescriptionException;
}

