package gr.nrallakis.superminesweeper.game.stats;

import java.io.IOException;
import java.util.List;

public interface RoundsRepository {
    void save(Round round);
    List<Round> getLastFiveRounds() throws IOException;
}
