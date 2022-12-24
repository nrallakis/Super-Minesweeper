package gr.nrallakis.superminesweeper.scenario;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScenarioRepositoryTest {
    final ScenarioFactory factory = new ScenarioFactory();
    final ScenarioFileRepository repository = new ScenarioFileRepository("medialab/scenarios/");

    @Test
    void save_easy_scenario_to_file() throws IOException {
        var scenario = factory.buildScenario(1, 10, 130, false);
        repository.save("easy_test", scenario);
        var path = Paths.get("medialab/scenarios/easy_test.txt");
        List<String> content = Files.readAllLines(path);
        List<String> expected = Arrays.asList(
                "1",    // difficulty
                "10",   // mines
                "130",  // seconds
                "0"     // Super-mines allowed
        );
        assertEquals(content, expected);
        Files.deleteIfExists(path);
    }

    @Test
    void save_hard_scenario_to_file() throws IOException {
        var scenario = factory.buildScenario(2,45, 250, true);
        repository.save("hard_test", scenario);
        var path = Paths.get("medialab/scenarios/hard_test.txt");
        List<String> content = Files.readAllLines(path);
        List<String> expected = Arrays.asList(
                "2",    // difficulty
                "45",   // mines
                "250",  // seconds
                "1"     // Super-mines allowed
        );
        assertEquals(content, expected);
        Files.deleteIfExists(path);
    }
}
