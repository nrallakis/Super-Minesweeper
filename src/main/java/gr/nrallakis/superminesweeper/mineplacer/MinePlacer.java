package gr.nrallakis.superminesweeper.mineplacer;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.MineCell;

import java.util.ArrayList;
import java.util.Collections;

public interface MinePlacer {
    void placeMines(BoardCell[][] cells, int minesCount, boolean addSuperMine);
}
