package gr.nrallakis.superminesweeper.ui;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import gr.nrallakis.superminesweeper.cell.EmptyCell;
import gr.nrallakis.superminesweeper.cell.MineCell;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CellWidget extends Group {
    private final BoardCell cell;
    private final int x;
    private final int y;
    private final double size;

    public CellWidget(BoardCell cell, int x, int y, double size) {
        this.cell = cell;
        this.x = x;
        this.y = y;
        this.size = size;
        draw();
    }

    public void draw() {
        Image image;
        if (cell.isRevealed()) {
            if (cell instanceof MineCell) {
                if (((MineCell) cell).isRevealedByUser()) {
                    image = CellImage.HITMINE;
                } else {
                    image = CellImage.MINE;
                }
            } else {
                int neighbours = ((EmptyCell) cell).getNeighbourMines();
                image = CellImage.getDigit(neighbours);
            }
        } else {
            if (cell.isMarkedAsMine()) {
                image = CellImage.FLAG;
            } else {
                image = CellImage.UNREVEALED;
            }
        }

        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        getChildren().add(imageView);
    }
}