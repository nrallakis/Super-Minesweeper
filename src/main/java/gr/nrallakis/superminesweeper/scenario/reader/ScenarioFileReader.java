package gr.nrallakis.superminesweeper.scenario.reader;


import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.reader.exceptions.InvalidValueException;
import gr.nrallakis.superminesweeper.scenario.reader.parser.ScenarioParser;
import gr.nrallakis.superminesweeper.scenario.reader.parser.ScenarioTextParser;
import gr.nrallakis.superminesweeper.scenario.reader.parser.validations.ScenarioValidator;

public class ScenarioFileReader implements ScenarioReader {
    @Override
    public Scenario read(String path) throws InvalidValueException, InvalidDescriptionException {
        //Read file from path
        String scenarioText = "2\n35\n240\n1";

        ScenarioParser parser = new ScenarioTextParser();
        Scenario scenario = parser.parse(scenarioText);
        ScenarioValidator validator = new ScenarioValidator();
        if (validator.isValid(scenario)) {
            return scenario;
        } else {
            throw new InvalidValueException();
        }
    }
}
