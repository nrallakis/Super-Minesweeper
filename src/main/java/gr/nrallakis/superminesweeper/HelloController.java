package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.ui.CellWidget;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HelloController {
    @FXML
    private Pane cellGrid;
    private int size = 432;
    private int spots = 16;
    private int squareSize = size / spots;
    private CellWidget[][] grid;


    @FXML
    public void initialize() {
        grid = new CellWidget[spots][spots];
        for (int i = 0; i < size; i += squareSize) {
            for (int j = 0; j < size; j += squareSize) {
                CellWidget r = new CellWidget(i, j, squareSize);
                grid[i / squareSize][j / squareSize] = r;
                cellGrid.getChildren().add(r);
            }
        }
        cellGrid.setOnMousePressed(this::gridPressed);
    }

//    public void released(MouseEvent event, Piece p) {
//        int gridx = (int)p.getX() / squareSize;
//        int gridy = (int)p.getY() / squareSize;
//        grid[gridx][gridy].setFill(Color.CRIMSON);
//        p.setX(squareSize / 2 + squareSize * gridx);
//        p.setY(squareSize / 2 + squareSize * gridy);
//        p.draw();
//    }

    public void gridPressed(MouseEvent event) {
        System.out.println(event.getX() + " " + event.getY());
        int x = (int) event.getX() / squareSize;
        int y = (int) event.getY() / squareSize;
        if (event.isPrimaryButtonDown()) {
//            grid[x][y].setFill(Color.BLACK);
        } else {
//            grid[x][y].setFill(Color.YELLOW);
//            grid[x][y].set
        }
    }

    @FXML
    protected void onExit() {
        Platform.exit();
    }
}