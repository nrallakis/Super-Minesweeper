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

public class Game {
    private final Board board;
    private final Scenario scenario;
    private final GameTimer timer;
    private final GameListener listener;
    private int tries;
    private GameState state;

    public Game(Scenario scenario) {
        this(scenario, new RandomMinePlacer(), new CountDownTimer(scenario.totalTime), (win) -> {});
    }

    public Game(Scenario scenario, GameListener listener) {
        this(scenario, new RandomMinePlacer(), new CountDownTimer(scenario.totalTime), listener);
    }

    public Game(Scenario scenario, MinePlacer minePlacer) {
        this(scenario, minePlacer, new CountDownTimer(scenario.totalTime), (win) -> {});
    }

    public Game(Scenario scenario, MinePlacer minePlacer, GameTimer timer, GameListener listener) {
        this.scenario = scenario;
        this.board = new Board(scenario, minePlacer);
        this.timer = timer;
        this.listener = listener;
        writeMinesToFile();
        start();
    }

    public void start() {
        state = GameState.RUNNING;
        timer.setOnTimeoutListener(this::showSolutionAndFinishGame);
        timer.start();
    }

    public boolean isRunning() {
        return state == GameState.RUNNING;
    }

    public void showSolutionAndFinishGame() {
        state = GameState.LOST;
        for (var cell : board.getMines()) {
            cell.reveal();
        }
        timer.stop();
        listener.onFinish(false);
    }

    private void writeMinesToFile() {
        MineFileWriter writer = new MineFileWriter("mines.txt", board.getMines());
        writer.write();
    }

    public void rightClickCell(int x, int y) {
        var cell = board.cells[x][y];
        boolean isMarkedAsMine = cell.isMarkedAsMine();
        if (isMarkedAsMine) {
            cell.setMarkedAsMine(false);
        } else {
            if (getMarkedMines() == getTotalMines()) return;
            cell.setMarkedAsMine(true);
            boolean isSuperMine = cell instanceof MineCell && ((MineCell) cell).isSuper;
            if (isSuperMine && tries < 4) {
                board.superMineMarked((MineCell) cell);
            }
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
                    board.revealNeighbourCellsWithNoNeighbourMines((EmptyCell) cell);
                }
                tries++;
                if (board.getRemainingEmptyCells() == 0) {
                    state = GameState.WON;
                    timer.stop();
                    listener.onFinish(true);
                }
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
        int markedMines = 0;
        for (int x = 0; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {
                if (getCells()[x][y].isMarkedAsMine()) {
                    markedMines++;
                }
            }
        }
        return markedMines;
    }

    BoardCell[][] getCells() {
        return board.getCells();
    }
}
