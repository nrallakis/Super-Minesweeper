package gr.nrallakis.superminesweeper.game.scenario;

import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidValueException;
import gr.nrallakis.superminesweeper.game.scenario.parser.ScenarioParser;
import gr.nrallakis.superminesweeper.game.scenario.parser.ScenarioTextParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScenarioFileRepository implements ScenarioRepository {
    private final String basePath;
    private final ScenarioParser parser;

    public ScenarioFileRepository(String path) {
        this(path, new ScenarioTextParser());
    }

    public ScenarioFileRepository(String path, ScenarioParser parser) {
        this.basePath = path;
        this.parser = parser;
    }

    @Override
    public Scenario get(String name) throws InvalidValueException, InvalidDescriptionException, IOException {
        String fileName = name + ".txt";
        var path = Paths.get(basePath, fileName);
        String serializedScenario = Files.readString(path);
        return parser.parse(serializedScenario);
    }

    @Override
    public List<String> getAllNames() {
        var path = Paths.get(basePath);
        try (Stream<Path> paths = Files.list(path)) {
            return paths.map(this::getScenarioNameFromPath).collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(String name) {
        String fileName = name + ".txt";
        File file = new File(basePath, fileName);
        file.delete();
    }

    @Override
    public void save(String name, Scenario scenario) {
        String fileName = name + ".txt";
        File file = new File(basePath, fileName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(scenario.getRules().getDifficulty() + "\n");
            writer.write(scenario.getMinesCount() + "\n");
            writer.write(scenario.getTotalTime() + "\n");
            writer.write(scenario.hasSuperMine() ? "1" : "0");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getScenarioNameFromPath(Path path) {
        String pathText = path.getFileName().toString();
        return pathText.split("\\.")[0];
    }
}
