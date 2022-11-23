module gr.nrallakis.superminesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens gr.nrallakis.superminesweeper to javafx.fxml;
    exports gr.nrallakis.superminesweeper;
    exports gr.nrallakis.superminesweeper.mineplacer;
    opens gr.nrallakis.superminesweeper.mineplacer to javafx.fxml;
    exports gr.nrallakis.superminesweeper.timer;
    opens gr.nrallakis.superminesweeper.timer to javafx.fxml;
}