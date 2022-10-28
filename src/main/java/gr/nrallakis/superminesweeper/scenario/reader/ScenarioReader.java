package gr.nrallakis.superminesweeper.scenario.reader;

import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidValueException;

public interface ScenarioReader {
    Scenario read(String path) throws InvalidValueException, InvalidDescriptionException;
}
