package gr.nrallakis.superminesweeper.game;

import gr.nrallakis.superminesweeper.game.cell.MineCell;
import gr.nrallakis.superminesweeper.game.minewriter.MineFileWriter;
import gr.nrallakis.superminesweeper.game.minewriter.MineWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MineFileWriterTest {
    @Test
    void mine_to_text() {
        var mine = new MineCell(3, 2, false);
        assertEquals(mine.toText(), "3,2,0");
    }

    @Test
    void super_mine_to_text() {
        var superMine = new MineCell(5, 1, true);
        assertEquals(superMine.toText(), "5,1,1");
    }

    @Test
    void write_mines_to_file() throws IOException {
        var mines = Arrays.asList(
            new MineCell(3, 2, false),
            new MineCell(5, 4, false),
            new MineCell(2, 9, true)
        );

        MineWriter mineFileWriter = new MineFileWriter("mines_test.txt", mines);
        mineFileWriter.write();
        var path = Paths.get("medialab/mines_test.txt");
        List<String> content = Files.readAllLines(path);
        List<String> expected = Arrays.asList("3,2,0","5,4,0","2,9,1");
        assertEquals(content, expected);
        Files.deleteIfExists(path);
    }
}
