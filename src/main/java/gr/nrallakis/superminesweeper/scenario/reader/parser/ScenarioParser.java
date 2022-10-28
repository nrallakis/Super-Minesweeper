package gr.nrallakis.superminesweeper.scenario.reader.parser;


import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidValueException;

public interface ScenarioParser {
    Scenario parse(String input) throws InvalidValueException, InvalidDescriptionException;
}

