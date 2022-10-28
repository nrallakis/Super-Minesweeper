module gr.nrallakis.superminesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens gr.nrallakis.superminesweeper to javafx.fxml;
    exports gr.nrallakis.superminesweeper;
}