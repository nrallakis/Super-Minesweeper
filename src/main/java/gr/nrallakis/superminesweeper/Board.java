package gr.nrallakis.superminesweeper;


import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.mineplacer.MinePlacer;
import gr.nrallakis.superminesweeper.mineplacer.RandomMinePlacer;
import gr.nrallakis.superminesweeper.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;


public class Board {
    final BoardCell[][] cells;
    final Scenario scenario;
    final int size;
    public Board(Scenario scenario, MinePlacer minePlacer) {
        this.scenario = scenario;
        this.size = scenario.boardSize;
        this.cells = new BoardCell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = new EmptyCell(x, y);
            }
        }

        minePlacer.placeMines(cells, scenario.minesCount, scenario.hasSuperMine);
        calculateNeighbours();
    }

    public void superMineMarked(MineCell cell) {
        if (!cell.isSuper) return;
        int x = cell.x;
        int y = cell.y;
        for (int i = 0; i < size; i++) {
            cells[x][i].reveal();
        }
        for (int i = 0; i < size; i++) {
            cells[i][y].reveal();
        }
    }

    public List<EmptyCell> getNeighbourCellsWithNoNeighbours(int x, int y) {
        var emptyNeighbours = new ArrayList<EmptyCell>();
        if (isEmptyCellWithNoNeighbours(x-1, y-1)) emptyNeighbours.add((EmptyCell) cells[x-1][y-1]);
        if (isEmptyCellWithNoNeighbours(x, y-1)) emptyNeighbours.add((EmptyCell) cells[x][y-1]);
        if (isEmptyCellWithNoNeighbours(x+1, y-1)) emptyNeighbours.add((EmptyCell) cells[x+1][y-1]);

        if (isEmptyCellWithNoNeighbours(x-1, y)) emptyNeighbours.add((EmptyCell) cells[x-1][y]);
        if (isEmptyCellWithNoNeighbours(x+1, y)) emptyNeighbours.add((EmptyCell) cells[x+1][y]);

        if (isEmptyCellWithNoNeighbours(x-1, y+1)) emptyNeighbours.add((EmptyCell) cells[x-1][y+1]);
        if (isEmptyCellWithNoNeighbours(x, y+1)) emptyNeighbours.add((EmptyCell) cells[x][y+1]);
        if (isEmptyCellWithNoNeighbours(x+1, y+1)) emptyNeighbours.add((EmptyCell) cells[x+1][y+1]);
        return emptyNeighbours;
    }

    private void calculateNeighbours() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                var cell = cells[x][y];
                if (cell instanceof EmptyCell) {
                    ((EmptyCell) cell).setNeighbourMines(getNeighbourMinesCount(x, y));
                }
            }
        }
    }

    public void revealNeighbourCellsWithNoNeighbours(EmptyCell cell) {
        var neighbours = getNeighbourCellsWithNoNeighbours(cell.x, cell.y);
        for (var neighbourCell : neighbours) {
            if (neighbourCell.isNotRevealed()) {
                neighbourCell.reveal();
                revealNeighbourCellsWithNoNeighbours(neighbourCell);
            }
        }
    }

    private int getNeighbourMinesCount(int x, int y) {
        int neighbours = 0;
        if (isMine(x-1, y-1)) neighbours++;
        if (isMine(x, y-1)) neighbours++;
        if (isMine(x+1, y-1)) neighbours++;

        if (isMine(x-1, y)) neighbours++;
        if (isMine(x+1, y)) neighbours++;

        if (isMine(x-1, y+1)) neighbours++;
        if (isMine(x, y+1)) neighbours++;
        if (isMine(x+1, y+1)) neighbours++;
        return neighbours;
    }

    private boolean isEmptyCellWithNoNeighbours(int x, int y) {
        boolean isValidPosition = x >= 0 && x <= size-1 && y >= 0 && y <= size-1;
        if (isValidPosition) {
            return cells[x][y] instanceof EmptyCell
                    && ((EmptyCell) cells[x][y]).getNeighbourMines() == 0;
        }
        return false;
    }

    private boolean isMine(int x, int y) {
        boolean isValidPosition = x >= 0 && x <= size-1 && y >= 0 && y <= size-1;
        if (isValidPosition) {
            return cells[x][y] instanceof MineCell;
        }
        return false;
    }

    BoardCell[][] getCells() {
        return cells;
    }

    List<MineCell> getMines() {
        List<MineCell> mines = new ArrayList<>();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                var cell = cells[x][y];
                if (cell instanceof MineCell) {
                    mines.add((MineCell)cell);
                }
            }
        }
        return mines;
    }
}
