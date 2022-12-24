package gr.nrallakis.superminesweeper.mineplacer;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.MineCell;

import java.util.ArrayList;
import java.util.Collections;

public class RandomMinePlacer implements MinePlacer {
    @Override
    public void placeMines(BoardCell[][] cells, int minesCount, boolean addSuperMine) {
        var allCells = new ArrayList<BoardCell>();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                allCells.add(cells[x][y]);
            }
        }
        Collections.shuffle(allCells);
        for (int i = 0; i < minesCount; i++) {
            int x = allCells.get(i).getX();
            int y = allCells.get(i).getY();
            cells[x][y] = new MineCell(x, y);
        }
        if (addSuperMine) {
            // Without loss of generality, make the first mine super
            int x = allCells.get(0).getX();
            int y = allCells.get(0).getY();
            cells[x][y] = new MineCell(x, y, true);
        }
    }
}
