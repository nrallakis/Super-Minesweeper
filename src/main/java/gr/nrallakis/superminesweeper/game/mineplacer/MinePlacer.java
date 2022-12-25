package gr.nrallakis.superminesweeper.game.mineplacer;

import gr.nrallakis.superminesweeper.game.cell.BoardCell;
public interface MinePlacer {
    void placeMines(BoardCell[][] cells, int minesCount, boolean addSuperMine);
}
