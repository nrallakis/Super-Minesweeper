package gr.nrallakis.superminesweeper.scenario.parser;


import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidValueException;

public interface ScenarioParser {
    Scenario parse(String input) throws InvalidValueException, InvalidDescriptionException;
}

