package gr.nrallakis.superminesweeper;


import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.scenario.Scenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    final BoardCell[][] board;
    public Board(Scenario scenario) {
        int size = scenario.boardSize;
        this.board = new BoardCell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = new EmptyCell(x, y);
            }
        }
        addMinesAtRandomPositions(scenario.minesCount, scenario.hasSuperMine);
    }

    void addMinesAtRandomPositions(int minesCount, boolean addOneSuperMine) {
        var xPositions = new ArrayList<Integer>();
        var yPositions = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            xPositions.add(i);
            yPositions.add(i);
        }
        Collections.shuffle(xPositions);
        Collections.shuffle(yPositions);
        for (int i = 0; i < minesCount; i++) {
            int x = xPositions.get(i);
            int y = yPositions.get(i);
            board[x][y] = new MineCell(x, y);
        }
        if (addOneSuperMine) {
            // Without loss of generality, make the first mine super
            int x = xPositions.get(0);
            int y = yPositions.get(0);
            board[x][y] = new MineCell(x, y, true);
        }
    }

    List<MineCell> getMines() {
        List<MineCell> mines = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                var cell = board[x][y];
                if (cell instanceof MineCell) {
                    mines.add((MineCell)cell);
                }
            }
        }
        return mines;
    }
}
