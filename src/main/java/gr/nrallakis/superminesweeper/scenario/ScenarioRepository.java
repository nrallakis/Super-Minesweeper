package gr.nrallakis.superminesweeper.scenario;

import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidValueException;

import java.util.List;

public interface ScenarioRepository {
    Scenario get(String name) throws Exception;

    List<String> getAllNames();

    boolean delete(String name);

    void save(String name, Scenario scenario);

}
