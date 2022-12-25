package gr.nrallakis.superminesweeper.game.stats;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundsFileRepository implements RoundsRepository {
    @Override
    public void save(Round round) {
        try {
            File file = new File("medialab", "rounds.txt");
            FileWriter writer = new FileWriter(file, true);
            String line = round.getTotalMines() + ","
                    + round.getTotalTries() + ","
                    + round.getTotalTime() + ","
                    + (round.hasUserWon() ? 1 : 0) + "\n";
            writer.write(line);
            writer.close();
        } catch (IOException e) {

        }
    }

    @Override
    public List<Round> getLastFiveRounds() throws IOException {
        var path = Paths.get("medialab/rounds.txt");
        List<String> content = Files.readAllLines(path);
        Collections.reverse(content);

        var rounds = new ArrayList<Round>();
        // Handle the case that we have less than 5 rounds
        int roundCount = Math.min(5, content.size());
        for (int i = 0; i < roundCount; i++) {
            String[] roundData = content.get(i).split(",");
            int totalMines = Integer.parseInt(roundData[0]);
            int totalTries = Integer.parseInt(roundData[1]);
            int totalTime = Integer.parseInt(roundData[2]);
            boolean userWon = Integer.parseInt(roundData[3]) == 1;
            Round round = new Round(totalMines, totalTries, totalTime, userWon);
            rounds.add(round);
        }
        return rounds;
    }
}
