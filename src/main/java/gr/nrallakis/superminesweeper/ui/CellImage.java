package gr.nrallakis.superminesweeper.ui;

import javafx.scene.image.Image;

public class CellImage {
    public static final Image UNREVEALED = loadImage("unrevealed.png");
    public static final Image FLAG = loadImage("flag.png");
    public static final Image EMPTY = loadImage("empty.png");
    public static final Image MINE = loadImage("mine.png");
    public static final Image HITMINE = loadImage("hitmine.png");
    public static final Image WRONGMINE = loadImage("wrongmine.png");
    private static final Image[] digits = new Image[9];

    static {
        digits[0] = EMPTY;

        for(int i=1; i < digits.length; i++) {
            digits[i] = loadImage(String.format("number%d.png", i));
        }
    }

    public static Image getDigit(int index) {
        if (index < 0 || index > 8) return null;
        return digits[index];
    }

    private static Image loadImage(String path) {
        return new Image("/" + path);
    }
}