package gr.nrallakis.superminesweeper.scenario;

import gr.nrallakis.superminesweeper.MineFileWriter;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.scenario.writer.ScenarioFileWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScenarioWriterTest {

    final ScenarioFactory factory = new ScenarioFactory();

    @Test
    void write_easy_scenario_to_file() throws IOException {
        var scenario = factory.buildScenario(1, 10, 130, false);
        ScenarioFileWriter writer = new ScenarioFileWriter();
        writer.write("easy_test", scenario);
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
    void write_hard_scenario_to_file() throws IOException {
        var scenario = factory.buildScenario(2,45, 250, true);
        ScenarioFileWriter writer = new ScenarioFileWriter();
        writer.write("hard_test", scenario);
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
