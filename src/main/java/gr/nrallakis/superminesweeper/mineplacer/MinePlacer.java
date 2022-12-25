package gr.nrallakis.superminesweeper.mineplacer;

import gr.nrallakis.superminesweeper.cell.BoardCell;
public interface MinePlacer {
    void placeMines(BoardCell[][] cells, int minesCount, boolean addSuperMine);
}
