package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.mineplacer.RandomMinePlacer;
import gr.nrallakis.superminesweeper.scenario.HardScenario;
import gr.nrallakis.superminesweeper.scenario.Scenario;
import gr.nrallakis.superminesweeper.ui.CellWidget;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private Pane cellGrid;
    @FXML
    private Label minesLabel;
    @FXML
    private Label markedMinesLabel;
    @FXML
    private Label timeLeftLabel;
    private final int windowWidth = 432;
    private int squareSize;
    private CellWidget[][] grid;
    private Game game;

    @FXML
    public void initialize() {
        var exampleScenario = new HardScenario(20, 5, true);
        this.game = new Game(exampleScenario);

        int boardSize = exampleScenario.boardSize;
        grid = new CellWidget[boardSize][boardSize];

        // Size in pixels
        this.squareSize = windowWidth / boardSize;
        var cells = game.getCells();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                CellWidget r = new CellWidget(cells[x][y], x * squareSize, y * squareSize, squareSize);
                grid[x][y] = r;
                cellGrid.getChildren().add(r);
            }
        }
        cellGrid.setOnMousePressed(this::gridPressed);
        game.setOnTimeChanged(this::updateTimeLabel);
        draw();
    }

    private void updateTimeLabel(int secondsLeft) {
        Platform.runLater(() -> {
            timeLeftLabel.setText("Time left: " + secondsLeft);
            draw();
        });
    }

    public void gridPressed(MouseEvent event) {
        int x = (int) event.getX() / squareSize;
        int y = (int) event.getY() / squareSize;
        if (event.isPrimaryButtonDown()) {
            game.clickCell(x, y);
        } else {
            game.rightClickCell(x, y);
        }
        draw();
    }

    public void draw() {
        int size = grid.length;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y].draw();
            }
        }
        minesLabel.setText("Mines: " + game.getTotalMines());
        markedMinesLabel.setText("Marked mines: " + game.getMarkedMines());
    }

    public void onSolutionClicked() {
        game.showSolutionAndFinishGame();
        draw();
    }

    @FXML
    protected void onExit() {
        Platform.exit();
    }
}