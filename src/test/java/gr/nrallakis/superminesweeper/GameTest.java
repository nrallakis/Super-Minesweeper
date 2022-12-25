package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.mineplacer.RandomMinePlacer;
import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.scenario.ScenarioFactory;
import gr.nrallakis.superminesweeper.scenario.ScenarioRules;
import gr.nrallakis.superminesweeper.stats.Round;
import gr.nrallakis.superminesweeper.stats.RoundsFileRepository;
import gr.nrallakis.superminesweeper.stats.RoundsRepository;
import gr.nrallakis.superminesweeper.timer.GameTimer;
import gr.nrallakis.superminesweeper.timer.TimeChangedListener;
import gr.nrallakis.superminesweeper.timer.TimeoutListener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    final ScenarioFactory scenarioFactory = new ScenarioFactory();
    final Scenario sampleScenario = scenarioWithNMines(35);

    Scenario scenarioWithNMines(int mines) {
        return scenarioFactory.buildScenario(2, mines, 240, true);
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
        game.rightClickCell(superMine.getX(), superMine.getY());

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

    @SuppressWarnings("GrazieInspection")
    @Test
    void clicking_an_empty_cell_reveals_neighbour_empty_cells() {
        // Let o: not revealed, r: revealed, M: mine (not revealed)
        // Suppose we have the following
        //   0 1 2 3 4 5
        //0  o o 1 1 1 o
        //1  o o 1 M 2 1
        //2  o o 1 2 M 1
        //3  o o o 1 1 1
        //4  o o o o o o
        //5  o o o o o o

        // Clicking on 0, 0
        // Should reveal the following
        //0  r r r o o o
        //1  r r r M o o
        //2  r r r r M o
        //3  r r r r r r
        //4  r r r r r r
        //5  r r r r r r
        var rules = new ScenarioRules(1, 6, 1, 2, 100, 100, false);
        var scenario = new Scenario(2, 100, false, rules);
        Game game = new Game(scenario, (cells, minesCount, addSuperMine) -> {
            //Place the mines
            cells[3][1] = new MineCell(3, 1);
            cells[4][2] = new MineCell(4, 2);
        });
        var cells = game.getCells();
        var emptyCell = cells[0][0];
        assertInstanceOf(EmptyCell.class, emptyCell);

        game.clickCell(emptyCell.getX(), emptyCell.getY());
        for (int y = 0; y <= 2; y++) {
            assertTrue(cells[0][y].isRevealed());
            assertTrue(cells[1][y].isRevealed());
            assertTrue(cells[2][y].isRevealed());
        }

        for (int x = 0; x < cells.length; x++) {
            assertTrue(cells[x][3].isRevealed());
            assertTrue(cells[x][4].isRevealed());
            assertTrue(cells[x][5].isRevealed());
        }

        // The corner cell that has 2 neighbour mines
        assertTrue(cells[2][2].isRevealed());

        assertFalse(cells[3][0].isRevealed());
        assertFalse(cells[4][0].isRevealed());
        assertFalse(cells[5][0].isRevealed());

        assertFalse(cells[3][1].isRevealed());
        assertFalse(cells[4][1].isRevealed());
        assertFalse(cells[5][1].isRevealed());

        assertFalse(cells[4][2].isRevealed());
        assertFalse(cells[5][2].isRevealed());
    }

    @Test
    void revealing_cells_by_clicking_empty_cell_should_unmark_the_marked_mines() {
        var rules = new ScenarioRules(1, 2, 1, 2, 100, 100, false);
        var scenario = new Scenario(2, 100, false, rules);
        Game game = new Game(scenario, (cells, minesCount, addSuperMine) -> {
        });
        assertEquals(game.getMarkedMines(), 0);
        game.rightClickCell(1, 0);
        assertEquals(game.getMarkedMines(), 1);
        game.clickCell(0, 0);
        assertEquals(game.getMarkedMines(), 0);
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
        game.rightClickCell(superMine.getX(), superMine.getY());

        int actualRevealed = getRevealedCellsCount(cells);

        // Assert
        assertEquals(expectedRevealed, actualRevealed);
        assertTrue(superMine.isMarkedAsMine());
    }

    @Test
    void writes_mines_to_file() throws IOException {
        new Game(scenarioWithNMines(2), (cells, minesCount, addSuperMine) -> {
            cells[1][2] = new MineCell(1, 2, false);
            cells[2][3] = new MineCell(2, 3, true);
        });

        var path = Paths.get("medialab/mines.txt");
        List<String> content = Files.readAllLines(path);
        List<String> expected = Arrays.asList("1,2,0", "2,3,1");
        assertEquals(content, expected);
        Files.deleteIfExists(path);
    }

    @Test
    void write_round_to_file_when_game_ends() throws IOException {
        var fakeGameTimer = new GameTimer() {
            @Override
            public void start() {}
            @Override
            public void stop() {}
            @Override
            public void setOnTimeoutListener(TimeoutListener listener) {}
            @Override
            public void setTimeChangedListener(TimeChangedListener listener) {}
            @Override
            public int getTimeLeft() {
                return 235;
            }
        };

        Game game = new Game(scenarioWithNMines(0), (cells, minesCount, addSuperMine) -> {}, fakeGameTimer);
        assertTrue(game.isRunning());
        game.clickCell(0, 0); // 1 try, the game is finished immediately, thus the timer will be 240
        assertFalse(game.isRunning());

        RoundsRepository roundsRepository = new RoundsFileRepository();
        Round round = roundsRepository.getLastFiveRounds().get(0);
        assertEquals(1, round.getTotalTries());
        assertEquals(0, round.getTotalMines());
        assertEquals(235, round.getTotalTime());
        assertTrue(round.hasUserWon());
    }

    @Test
    void marking_a_mine_changes_marked_mines_counter() {
        // Arrange
        Game game = new Game(sampleScenario, new RandomMinePlacer());

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
        game.rightClickCell(1, 1);
        assertFalse(cells[1][1].isMarkedAsMine());

        // Marking one more cell, should get marked
        game.rightClickCell(3, 3);
        assertTrue(cells[3][3].isMarkedAsMine());
    }
}
