package gr.nrallakis.superminesweeper.scenario.writer;

import gr.nrallakis.superminesweeper.scenario.Scenario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScenarioFileWriter {
    public void write(String name, Scenario scenario) {
        try {
            String fileName = name + ".txt";
            File file = new File("medialab/scenarios", fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(scenario.rules.difficulty + "\n");
            writer.write(scenario.minesCount + "\n");
            writer.write(scenario.totalTime + "\n");
            writer.write(scenario.hasSuperMine ? "1" : "0");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
