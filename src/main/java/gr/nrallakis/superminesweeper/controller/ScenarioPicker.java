package gr.nrallakis.superminesweeper.controller;

import gr.nrallakis.superminesweeper.Application;
import gr.nrallakis.superminesweeper.game.scenario.Scenario;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioFileRepository;
import gr.nrallakis.superminesweeper.game.scenario.ScenarioRepository;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.game.scenario.exceptions.InvalidValueException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenarioPicker {
    @FXML
    ListView<String> scenarioList;

    private ScenarioRepository scenarioRepository;
    private ScenarioLoadListener listener;

    public static void show(ScenarioLoadListener listener) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("scenario-picker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Load scenario");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        ScenarioPicker form = fxmlLoader.getController();
        form.setListener(listener);
        stage.showAndWait();
    }

    public void setListener(ScenarioLoadListener listener) {
        this.listener = listener;
    }

    @FXML
    public void initialize() {
        scenarioRepository = new ScenarioFileRepository("medialab/scenarios/");
        refreshScenarioList();
    }

    public void refreshScenarioList() {
        scenarioList.getItems().clear();
        var scenarioNames = scenarioRepository.getAllNames();
        if (scenarioNames.isEmpty()) return;
        scenarioList.getItems().addAll(scenarioNames);
    }

    public void onDeleteClicked() {
        String selectedScenario = scenarioList.getSelectionModel().getSelectedItem();
        scenarioRepository.delete(selectedScenario);
        refreshScenarioList();
    }

    public void onLoadClicked() {
        String selectedScenario = scenarioList.getSelectionModel().getSelectedItem();
        try {
            Scenario scenario = scenarioRepository.get(selectedScenario);
            listener.onScenarioLoad(scenario);
            close();
        } catch (InvalidValueException e) {
            showErrorDialog("Invalid value");
        } catch (InvalidDescriptionException e) {
            showErrorDialog("Invalid description");
        } catch (IOException e) {
            showErrorDialog("Error loading file");
        }
    }

    private void showErrorDialog(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.showAndWait();
    }

    private void close() {
        ((Stage) scenarioList.getScene().getWindow()).close();
    }

    public interface ScenarioLoadListener {
        void onScenarioLoad(Scenario scenario);
    }
}
