package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.MineCell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MineFileWriter {
    private final List<MineCell> mines;
    private final String fileName;

    public MineFileWriter(String fileName, List<MineCell> mines) {
        this.fileName = fileName;
        this.mines = mines;
    }

    public void writeToFile() {
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file.getName());
            for (MineCell mine : mines) {
                String line = mine.x + "," + mine.y + "," + (mine.isSuper ? 1 : 0) + "\n";
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
