module gr.nrallakis.superminesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens gr.nrallakis.superminesweeper to javafx.fxml;
    exports gr.nrallakis.superminesweeper;
    exports gr.nrallakis.superminesweeper.game.mineplacer;
    exports gr.nrallakis.superminesweeper.game.scenario;
    exports gr.nrallakis.superminesweeper.game.cell;
    exports gr.nrallakis.superminesweeper.game.scenario.parser;
    exports gr.nrallakis.superminesweeper.game.scenario.exceptions;
    opens gr.nrallakis.superminesweeper.game.mineplacer to javafx.fxml;
    exports gr.nrallakis.superminesweeper.game.timer;
    opens gr.nrallakis.superminesweeper.game.timer to javafx.fxml;
    exports gr.nrallakis.superminesweeper.ui;
    opens gr.nrallakis.superminesweeper.ui to javafx.fxml;
    exports gr.nrallakis.superminesweeper.game;
    opens gr.nrallakis.superminesweeper.game to javafx.fxml;
    exports gr.nrallakis.superminesweeper.controller;
    opens gr.nrallakis.superminesweeper.controller to javafx.fxml;
    exports gr.nrallakis.superminesweeper.game.minewriter;
    opens gr.nrallakis.superminesweeper.game.minewriter to javafx.fxml;
}