package gr.nrallakis.superminesweeper.controller;

import gr.nrallakis.superminesweeper.game.Game;
import gr.nrallakis.superminesweeper.game.scenario.Scenario;
import gr.nrallakis.superminesweeper.game.stats.RoundsFileRepository;
import gr.nrallakis.superminesweeper.game.stats.RoundsRepository;
import gr.nrallakis.superminesweeper.ui.CellWidget;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    private static final int windowWidth = 432;
    @FXML
    private Pane cellGrid;
    @FXML
    private Label minesLabel;
    @FXML
    private Label markedMinesLabel;
    @FXML
    private Label timeLeftLabel;
    private int squareSize;
    private CellWidget[][] grid;
    private Game game;

    private Scenario currentScenario;

    @FXML
    public void initialize() {
        cellGrid.setOnMousePressed(this::gridPressed);
    }

    private void startGame(Scenario scenario) {
        if (game != null && game.isRunning()) {
            game.showSolutionAndFinishGame();
        }
        this.game = new Game(scenario, this::onGameFinish);

        int boardSize = scenario.getRules().getBoardSize();
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
        game.setOnTimeChanged(this::updateTimeLabel);
        draw();
    }

    private void onGameFinish(boolean isWin) {
        Alert alert;
        if (isWin) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You won! :)");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You lost! :/");
        }
        alert.setTitle("Message");
        alert.showAndWait();
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

    public void onCreateClicked() throws IOException {
        ScenarioForm.show();
    }

    public void onRestartClicked() {
        if (currentScenario == null) return;
        startGame(currentScenario);
    }

    public void onLoadClicked() throws IOException {
        ScenarioPicker.show(scenario -> {
            this.currentScenario = scenario;
            startGame(currentScenario);
        });
    }

    public void onRoundsClicked() throws IOException {
        RoundsRepository roundsRepository = new RoundsFileRepository();
        var rounds = roundsRepository.getLastFiveRounds();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Last 5 rounds:");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rounds.size(); i++) {
            var round = rounds.get(i);
            builder.append("Round ").append(i + 1).append(".\n");
            builder.append(round).append("\n\n");
        }

        alert.setContentText(builder.toString());
        alert.showAndWait();
    }

    @FXML
    protected void onExit() {
        Platform.exit();
    }
}