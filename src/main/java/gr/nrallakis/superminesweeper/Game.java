package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.scenario.Scenario;


public class Game {
    private final Scenario scenario;
    private final Board board;

    public Game(Scenario scenario) {
        this.scenario = scenario;
        board = new Board(scenario);
        writeMinesToFile();
    }

    private void writeMinesToFile() {
        MineFileWriter writer = new MineFileWriter("mines.txt", board.getMines());
        writer.writeToFile();
    }

    public void revealCell(int x, int y) {

    }

    public static final int EMPTY = 0;
    public static final int MINE = 1;
    public static final int SUPERMINE = 2;


    public void start() {

    }
}
