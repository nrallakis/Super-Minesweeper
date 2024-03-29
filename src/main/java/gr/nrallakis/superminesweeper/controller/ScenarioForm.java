package gr.nrallakis.superminesweeper.controller;

import gr.nrallakis.superminesweeper.Application;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioFactory;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioFileRepository;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioRepository;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioRules;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenarioForm {
    @FXML
    Label hasSuperMineLabel;
    @FXML
    CheckBox hasSuperMineCheckbox;
    @FXML
    TextField scenarioNameInput;
    @FXML
    ComboBox<String> difficultyDropDown;
    @FXML
    Spinner<Integer> minesSpinner;
    @FXML
    Spinner<Integer> totalTimeSpinner;
    @FXML
    Button createButton;

    private ScenarioRepository scenarioRepository;

    public static void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("scenario-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
        Stage stage = new Stage();
        stage.setTitle("New scenario");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    public void initialize() {
        scenarioRepository = new ScenarioFileRepository("medialab/scenarios/");
        difficultyDropDown.getItems().addAll("1: Easy", "2: Hard");

        // Select the first difficulty (Easy) as the initial selection
        difficultyDropDown.setValue(difficultyDropDown.getItems().get(0));
        onDifficultyChanged();
    }

    public void onDifficultyChanged() {
        var scenarioFactory = new ScenarioFactory();
        var scenarioRules = scenarioFactory.getRules(getDifficulty());
        setMinesSpinnerMinMax(scenarioRules);
        setTotalTimeSpinnerMinMax(scenarioRules);
        if (scenarioRules.isSuperMineAllowed()) {
            hasSuperMineLabel.setVisible(true);
            hasSuperMineCheckbox.setVisible(true);
        } else {
            hasSuperMineLabel.setVisible(false);
            hasSuperMineCheckbox.setVisible(false);
        }
    }

    private int getDifficulty() {
        return Integer.parseInt(difficultyDropDown.getValue().split(":")[0]);
    }

    public void onCreateClicked() {
        if (isFormValid()) {
            saveScenario();
            ((Stage) createButton.getScene().getWindow()).close();
        } else {
            System.out.println("You must provide a name for the new scenario");
        }
    }

    private boolean isFormValid() {
        return !scenarioNameInput.getText().isBlank();
    }

    private void saveScenario() {
        String scenarioName = scenarioNameInput.getText();
        var scenarioFactory = new ScenarioFactory();
        int difficulty = getDifficulty();
        int mines = minesSpinner.getValue();
        int totalTime = totalTimeSpinner.getValue();
        boolean hasSuperMine = hasSuperMineCheckbox.isSelected();

        var scenario = scenarioFactory.buildScenario(difficulty, mines, totalTime, hasSuperMine);
        scenarioRepository.save(scenarioName, scenario);
    }

    private void setMinesSpinnerMinMax(ScenarioRules rules) {
        var spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(rules.getMinMinesCount(), rules.getMaxMinesCount());
        minesSpinner.setValueFactory(spinnerFactory);
    }

    private void setTotalTimeSpinnerMinMax(ScenarioRules rules) {
        var spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(rules.getMinTotalTime(), rules.getMaxTotalTime());
        totalTimeSpinner.setValueFactory(spinnerFactory);
    }
}


