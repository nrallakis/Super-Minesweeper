package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.scenario.HardScenario;
import gr.nrallakis.superminesweeper.scenario.Scenario;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    final Scenario sampleScenario = scenarioWithNMines(35);

    Scenario scenarioWithNMines(int mines) {
        return new HardScenario(mines, 240, true);
    }

    int getRevealedCellsCount(BoardCell[][] cells) {
        int count = 0;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                if (cells[x][y].isRevealed()) count++;
            }
        }
        return count;
    }

    @Test
    void marking_a_super_mine_in_the_first_4_tries_reveals_cells_in_horizontal_and_vertical_axis() {
        // Arrange
        Game game = new Game(sampleScenario, (cells, minesCount, addSuperMine) -> {
            cells[3][3] = new MineCell(3, 3, true);
        });
        var cells = game.getCells();
        var superMine = cells[3][3];

        // Act
        game.rightClickCell(superMine.x, superMine.y);

        // Assert
        boolean horizontalRevealed = cells[0][3].isRevealed()
                && cells[1][3].isRevealed()
                && cells[2][3].isRevealed()
                && cells[3][3].isRevealed()
                && cells[4][3].isRevealed()
                && cells[5][3].isRevealed()
                && cells[6][3].isRevealed()
                && cells[7][3].isRevealed()
                && cells[8][3].isRevealed()
                && cells[9][3].isRevealed()
                && cells[10][3].isRevealed()
                && cells[11][3].isRevealed()
                && cells[12][3].isRevealed()
                && cells[13][3].isRevealed()
                && cells[14][3].isRevealed()
                && cells[15][3].isRevealed();

        boolean verticalRevealed = cells[3][0].isRevealed()
                && cells[3][1].isRevealed()
                && cells[3][2].isRevealed()
                && cells[3][3].isRevealed()
                && cells[3][4].isRevealed()
                && cells[3][5].isRevealed()
                && cells[3][6].isRevealed()
                && cells[3][7].isRevealed()
                && cells[3][8].isRevealed()
                && cells[3][9].isRevealed()
                && cells[3][10].isRevealed()
                && cells[3][11].isRevealed()
                && cells[3][12].isRevealed()
                && cells[3][13].isRevealed()
                && cells[3][14].isRevealed()
                && cells[3][15].isRevealed();

        assertTrue(horizontalRevealed && verticalRevealed);
    }

    @Test
    void clicking_an_empty_cell_reveals_neighbour_empty_cells() {
        Game game = new Game(scenarioWithNMines(sampleScenario.boardSize), (cells, minesCount, addSuperMine) -> {
            for (int i = 0; i < sampleScenario.boardSize; i++) {
                //Fill the third column with mines
                cells[2][i] = new MineCell(3, 3);
            }
        });
        var cells = game.getCells();
        var emptyCell = cells[0][0];
        assertInstanceOf(EmptyCell.class, emptyCell);

        game.clickCell(emptyCell.x, emptyCell.y);
        // The whole first column should be revealed
        // The second column should not be revealed
        boolean firstRevealed = cells[0][0].isRevealed()
                && cells[0][1].isRevealed()
                && cells[0][2].isRevealed()
                && cells[0][3].isRevealed()
                && cells[0][4].isRevealed()
                && cells[0][5].isRevealed()
                && cells[0][6].isRevealed()
                && cells[0][7].isRevealed()
                && cells[0][8].isRevealed()
                && cells[0][9].isRevealed()
                && cells[0][10].isRevealed()
                && cells[0][11].isRevealed()
                && cells[0][12].isRevealed()
                && cells[0][13].isRevealed()
                && cells[0][14].isRevealed()
                && cells[0][15].isRevealed();

        boolean secondRevealed = cells[1][0].isRevealed()
                && cells[1][1].isRevealed()
                && cells[1][2].isRevealed()
                && cells[1][3].isRevealed()
                && cells[1][4].isRevealed()
                && cells[1][5].isRevealed()
                && cells[1][6].isRevealed()
                && cells[1][7].isRevealed()
                && cells[1][8].isRevealed()
                && cells[1][9].isRevealed()
                && cells[1][10].isRevealed()
                && cells[1][11].isRevealed()
                && cells[1][12].isRevealed()
                && cells[1][13].isRevealed()
                && cells[1][14].isRevealed()
                && cells[1][15].isRevealed();
        assertTrue(firstRevealed && !secondRevealed);
    }

    @Test
    void marking_a_super_mine_after_4_tries_should_only_mark_the_mine() {
        // Arrange
        Game game = new Game(scenarioWithNMines(1), (cells, minesCount, addSuperMine) -> {
            cells[3][3] = new MineCell(3, 3, true);
        });
        var cells = game.getCells();
        var superMine = cells[3][3];

        // Act
        // 4 successful tries
        game.clickCell(2, 3);
        game.clickCell(4, 3);
        game.clickCell(3, 2);
        game.clickCell(3, 4);

        int expectedRevealed = getRevealedCellsCount(cells);

        // Marking the super mine after 4 tries should not reveal any more cells
        game.rightClickCell(superMine.x, superMine.y);

        int actualRevealed = getRevealedCellsCount(cells);

        // Assert
        assertEquals(expectedRevealed, actualRevealed);
        assertTrue(superMine.isMarkedAsMine());
    }

    @Test
    void writes_mines_to_file() throws IOException {
        new Game(scenarioWithNMines(2), (cells, minesCount, addSuperMine) -> {
            cells[1][2] = new MineCell(1, 2,false);
            cells[2][3] = new MineCell(2, 3, true);
        });

        List<String> content = Files.readAllLines(Paths.get("mines.txt"));
        List<String> expected = Arrays.asList("1,2,0","2,3,1");
        assertEquals(content, expected);
    }

    @Test
    void marking_a_mine_changes_marked_mines_counter() {
        // Arrange
        Game game = new Game(sampleScenario);

        assertEquals(0, game.getMarkedMines());
        game.rightClickCell(0, 0);
        assertEquals(1, game.getMarkedMines());

        game.rightClickCell(1, 1);
        assertEquals(2, game.getMarkedMines());
        game.rightClickCell(1, 1);
        assertEquals(1, game.getMarkedMines());
    }

    @Test
    void can_mark_as_many_mines_as_there_are() {
        // Arrange
        Game game = new Game(scenarioWithNMines(2), (cells, minesCount, addSuperMine) -> {
            cells[3][3] = new MineCell(3, 3, false);
            cells[3][4] = new MineCell(3, 4, false);
        });
        var cells = game.getCells();

        // Mark 2 cells as mines
        game.rightClickCell(0, 0);
        game.rightClickCell(1, 1);

        assertTrue(cells[0][0].isMarkedAsMine());
        assertTrue(cells[1][1].isMarkedAsMine());

        // Marking one more cell, should not get marked
        game.rightClickCell(3, 3);
        assertFalse(cells[3][3].isMarkedAsMine());

        // Un-marking one more cell, should enable marking again
        game.rightClickCell(1,1);
        assertFalse(cells[1][1].isMarkedAsMine());

        // Marking one more cell, should get marked
        game.rightClickCell(3, 3);
        assertTrue(cells[3][3].isMarkedAsMine());
    }
}
