package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.mineplacer.MinePlacer;
import gr.nrallakis.superminesweeper.mineplacer.RandomMinePlacer;
import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.timer.GameTimer;
import gr.nrallakis.superminesweeper.timer.CountDownTimer;
import gr.nrallakis.superminesweeper.timer.TimeChangedListener;

import java.util.Date;

public class Game {
    private final Board board;
    private final Scenario scenario;
    private Date startTime;
    private int remainingMines;
    private int timeLeft;
    private int markedMines;
    private int tries;
    private GameState state;
    private GameTimer timer;

    public Game(Scenario scenario) {
        this(scenario, new RandomMinePlacer(), new CountDownTimer(scenario.totalTime));
    }

    public Game(Scenario scenario, MinePlacer minePlacer) {
        this(scenario, minePlacer, new CountDownTimer(scenario.totalTime));
    }

    public Game(Scenario scenario, MinePlacer minePlacer, GameTimer timer) {
        this.scenario = scenario;
        this.board = new Board(scenario, minePlacer);
        this.timeLeft = scenario.totalTime;
        this.timer = timer;
        writeMinesToFile();
        start();
    }

    public void start() {
        state = GameState.RUNNING;
        timer.setOnTimeoutListener(this::showSolutionAndFinishGame);
        timer.start();
    }

    public void showSolutionAndFinishGame() {
        state = GameState.LOST;
        for (var cell : board.getMines()) {
            cell.reveal();
        }
    }

    private void writeMinesToFile() {
        MineFileWriter writer = new MineFileWriter("mines.txt", board.getMines());
        writer.writeToFile();
    }

    public void rightClickCell(int x, int y) {
        var cell = board.cells[x][y];
        boolean isMarkedAsMine = cell.isMarkedAsMine();
        if (isMarkedAsMine) {
            cell.setMarkedAsMine(false);
            markedMines--;
        } else {
            if (markedMines == getTotalMines()) return;
            cell.setMarkedAsMine(true);
            boolean isSuperMine = cell instanceof MineCell && ((MineCell) cell).isSuper;
            if (isSuperMine && tries < 4) {
                board.superMineMarked((MineCell) cell);
            }
            markedMines++;
        }
    }

    public void clickCell(int x, int y) {
        var cell = board.cells[x][y];
        if (cell.isNotRevealed()) {
            cell.reveal();
            if (cell instanceof MineCell) {
                ((MineCell) cell).revealedByUser();
                showSolutionAndFinishGame();
            } else if (cell instanceof EmptyCell){
                if (((EmptyCell) cell).getNeighbourMines() == 0) {
                    board.revealNeighbourCellsWithNoNeighbours((EmptyCell) cell);
                }
                tries++;
            }
        }
    }

    public void setOnTimeChanged(TimeChangedListener listener) {
        timer.setTimeChangedListener(listener);
    }

    int getTotalMines() {
        return scenario.minesCount;
    }

    int getMarkedMines() {
        return markedMines;
    }

    BoardCell[][] getCells() {
        return board.getCells();
    }
}
