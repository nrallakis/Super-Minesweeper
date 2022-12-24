package gr.nrallakis.superminesweeper;


import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.mineplacer.MinePlacer;
import gr.nrallakis.superminesweeper.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;


public class Board {
    final BoardCell[][] cells;
    final Scenario scenario;
    final int size;
    public Board(Scenario scenario, MinePlacer minePlacer) {
        this.scenario = scenario;
        this.size = scenario.rules.boardSize;
        this.cells = new BoardCell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = new EmptyCell(x, y);
            }
        }

        minePlacer.placeMines(cells, scenario.minesCount, scenario.hasSuperMine);
        calculateNeighbourMines();
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

    public List<EmptyCell> getNeighbourCellsThatAreNotMines(int x, int y) {
        var emptyNeighbours = new ArrayList<EmptyCell>();
        if (isEmptyCell(x-1, y-1)) emptyNeighbours.add((EmptyCell) cells[x-1][y-1]);
        if (isEmptyCell(x, y-1)) emptyNeighbours.add((EmptyCell) cells[x][y-1]);
        if (isEmptyCell(x+1, y-1)) emptyNeighbours.add((EmptyCell) cells[x+1][y-1]);

        if (isEmptyCell(x-1, y)) emptyNeighbours.add((EmptyCell) cells[x-1][y]);
        if (isEmptyCell(x+1, y)) emptyNeighbours.add((EmptyCell) cells[x+1][y]);

        if (isEmptyCell(x-1, y+1)) emptyNeighbours.add((EmptyCell) cells[x-1][y+1]);
        if (isEmptyCell(x, y+1)) emptyNeighbours.add((EmptyCell) cells[x][y+1]);
        if (isEmptyCell(x+1, y+1)) emptyNeighbours.add((EmptyCell) cells[x+1][y+1]);
        return emptyNeighbours;
    }

    private void calculateNeighbourMines() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                var cell = cells[x][y];
                if (cell instanceof EmptyCell) {
                    ((EmptyCell) cell).setNeighbourMines(getNeighbourMinesCount(x, y));
                }
            }
        }
    }

    public void revealNeighbourCellsWithNoNeighbourMines(EmptyCell targetCell) {
        var emptyNeighbours = getNeighbourCellsThatAreNotMines(targetCell.x, targetCell.y);
        for (var cell : emptyNeighbours) {
            if (cell.isNotRevealed()) {
                cell.reveal();
                if (cell.getNeighbourMines() == 0) {
                    revealNeighbourCellsWithNoNeighbourMines(cell);
                }
            }
        }
    }

    public int getRemainingEmptyCells() {
        int remainingCells = 0;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                var cell = cells[x][y];
                if (cell instanceof EmptyCell && cell.isNotRevealed()) {
                    remainingCells++;
                }
            }
        }
        return remainingCells;
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

    private boolean isEmptyCell(int x, int y) {
        return isValidPosition(x, y) && cells[x][y] instanceof EmptyCell;
    }

    private boolean isMine(int x, int y) {
        return isValidPosition(x, y) && cells[x][y] instanceof MineCell;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x <= size-1 && y >= 0 && y <= size-1;
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
