package gr.nrallakis.superminesweeper.ui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CellWidget extends StackPane {
    public CellWidget(int x, int y, double size) {
        Rectangle rectangle = new Rectangle(x, y, size, size);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.5);
        Text text = new Text("1");
        getChildren().addAll(rectangle, text);
    }
}
