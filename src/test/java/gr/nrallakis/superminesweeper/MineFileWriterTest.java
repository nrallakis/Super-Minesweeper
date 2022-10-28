package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.MineCell;
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

        MineFileWriter mineFileWriter = new MineFileWriter("mines_test.txt", mines);
        mineFileWriter.writeToFile();
        List<String> content = Files.readAllLines(Paths.get("mines_test.txt"));
        List<String> expected = Arrays.asList("3,2,0","5,4,0","2,9,1");
        assertEquals(content, expected);
    }
}
