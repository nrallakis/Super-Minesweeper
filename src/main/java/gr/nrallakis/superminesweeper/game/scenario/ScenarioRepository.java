package gr.nrallakis.superminesweeper.game.scenario;

import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidValueException;

import java.io.IOException;
import java.util.List;

public interface ScenarioRepository {
    Scenario get(String name) throws InvalidValueException, InvalidDescriptionException, IOException;
    List<String> getAllNames();
    void delete(String name);
    void save(String name, Scenario scenario);

}
