module gr.nrallakis.superminesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens gr.nrallakis.superminesweeper to javafx.fxml;
    exports gr.nrallakis.superminesweeper;
    exports gr.nrallakis.superminesweeper.mineplacer;
    exports gr.nrallakis.superminesweeper.scenario;
    exports gr.nrallakis.superminesweeper.cell;
    opens gr.nrallakis.superminesweeper.mineplacer to javafx.fxml;
    exports gr.nrallakis.superminesweeper.timer;
    opens gr.nrallakis.superminesweeper.timer to javafx.fxml;
    exports gr.nrallakis.superminesweeper.ui;
    opens gr.nrallakis.superminesweeper.ui to javafx.fxml;
}