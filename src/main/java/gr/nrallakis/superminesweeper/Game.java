package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import gr.nrallakis.superminesweeper.mineplacer.MinePlacer;
import gr.nrallakis.superminesweeper.mineplacer.RandomMinePlacer;
import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.stats.Round;
import gr.nrallakis.superminesweeper.stats.RoundsFileRepository;
import gr.nrallakis.superminesweeper.stats.RoundsRepository;
import gr.nrallakis.superminesweeper.timer.GameTimer;
import gr.nrallakis.superminesweeper.timer.CountDownTimer;
import gr.nrallakis.superminesweeper.timer.TimeChangedListener;

/**
 * Holds all the minesweeper game logic.
 * To start a game, use one of the Game constructors
 * and then use the {@link #start()} method.
 * To keep up to date with the remaining time use {@link #setOnTimeChanged(TimeChangedListener)}
 * To listen to game finishing use a {@link gr.nrallakis.superminesweeper.GameListener}
 *
 * @author Nicholas Rallakis
 *
 */
public class Game {
    private final Board board;
    private final Scenario scenario;
    private final GameTimer timer;
    private final GameListener listener;
    private int totalTries;
    private GameState state;

    /**
     * @param scenario The scenario to be used for the game.
     * @param listener The {@link GameListener} to be used on the game.
     */
    public Game(Scenario scenario, GameListener listener) {
        this(scenario, new RandomMinePlacer(), new CountDownTimer(scenario.getTotalTime()), listener);
    }

    protected Game(Scenario scenario, MinePlacer minePlacer, GameTimer timer) {
        this(scenario, minePlacer, timer, (win) -> {});
    }

    protected Game(Scenario scenario, MinePlacer minePlacer) {
        this(scenario, minePlacer, new CountDownTimer(scenario.getTotalTime()), (win) -> {});
    }

    protected Game(Scenario scenario, MinePlacer minePlacer, GameTimer timer, GameListener listener) {
        this.scenario = scenario;
        this.board = new Board(scenario, minePlacer);
        this.timer = timer;
        this.listener = listener;
        start();
    }

    /**
     * @return whether the game is still running.
     */
    public boolean isRunning() {
        return state == GameState.RUNNING;
    }

    /**
     * Reveals all the cells on the board and finished the game as lost.
     */
    public void showSolutionAndFinishGame() {
        for (var cell : board.getMines()) {
            cell.reveal();
        }
        gameFinished(GameState.LOST);
    }

    /**
     * Simulates right-clicking a cell in a game.
     * By the rules of Super-Minesweeper, right-clicking
     * a cell marks it as a mine.
     * In the less usual scenario that you mark a super-mine during
     * the 4 first left-clicks, the diagonal and horizontal
     * axis around the cell are revealed.
     *
     * @param x the x position of the cell
     * @param y the y position of the cell
     */
    public void rightClickCell(int x, int y) {
        var cell = board.getCells()[x][y];
        boolean isMarkedAsMine = cell.isMarkedAsMine();
        if (isMarkedAsMine) {
            cell.setMarkedAsMine(false);
        } else {
            if (getMarkedMines() == getTotalMines()) return;
            cell.setMarkedAsMine(true);
            boolean isSuperMine = cell instanceof MineCell && ((MineCell) cell).isSuper();
            if (isSuperMine && totalTries < 4) {
                board.superMineMarked((MineCell) cell);
            }
        }
    }

    /**
     * Simulates left-clicking a cell in a game.
     * By the rules of Super-Minesweeper, left-clicking a cell reveals the cell.
     * If the cell is already revealed, this method does nothing.
     * If the cell is a mine, the game ends as lost.
     * If the cell is empty, neighbouring empty cells are revealed recursively.
     * If the cell is the last non-mine cell on the board, the game ends as won.
     *
     * @param x the x position of the cell
     * @param y the y position of the cell
     */
    public void clickCell(int x, int y) {
        var cell = board.getCells()[x][y];
        if (cell.isRevealed()) return;
        cell.reveal();

        if (cell instanceof MineCell) {
            ((MineCell) cell).revealedByUser();
            showSolutionAndFinishGame();
        } else if (cell instanceof EmptyCell){
            totalTries++;
            var emptyCell = ((EmptyCell) cell);
            int neighbourMines = emptyCell.getNeighbourMines();
            if (neighbourMines == 0) {
                board.revealNeighbourCellsWithNoNeighbourMines(emptyCell);
            }
            if (board.areAllCellsRevealed()) {
                gameFinished(GameState.WON);
            }
        }
    }

    /**
     * @param listener a {@link TimeChangedListener} to keep up to date with the remaining time
     */
    public void setOnTimeChanged(TimeChangedListener listener) {
        timer.setTimeChangedListener(listener);
    }

    /**
     * @return the count of all the mines in the game
     */
    public int getTotalMines() {
        return scenario.getMinesCount();
    }

    /**
     * @return the count of all the marked mines in the game
     */
    public int getMarkedMines() {
        int markedMines = 0;
        var cells = board.getCells();
        for (int x = 0; x < board.getSize(); x++) {
            for (int y = 0; y < board.getSize(); y++) {
                if (cells[x][y].isMarkedAsMine()) {
                    markedMines++;
                }
            }
        }
        return markedMines;
    }

    /**
     * The returned 2D array should NOT be mutated using this method.
     * @return all the {@link gr.nrallakis.superminesweeper.cell.BoardCell} cells in the game
     */
    public BoardCell[][] getCells() {
        return board.getCells();
    }

    private void start() {
        writeMinesToFile();
        state = GameState.RUNNING;
        timer.setOnTimeoutListener(this::showSolutionAndFinishGame);
        timer.start();
    }

    private void writeMinesToFile() {
        MineWriter writer = new MineFileWriter("mines.txt", board.getMines());
        writer.write();
    }

    private void saveFinishedRound() {
        RoundsRepository roundsRepository = new RoundsFileRepository();
        boolean userWon = state == GameState.WON;
        int totalMines = getTotalMines();
        int timeLeft = timer.getTimeLeft();
        Round round = new Round(totalMines, totalTries, timeLeft, userWon);
        roundsRepository.save(round);
    }

    private void gameFinished(GameState state) {
        this.state = state;
        boolean isWin = state == GameState.WON;

        timer.stop();
        listener.onFinish(isWin);
        saveFinishedRound();
    }
}
