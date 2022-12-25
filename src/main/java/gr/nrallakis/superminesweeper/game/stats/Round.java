package gr.nrallakis.superminesweeper.game.stats;

public class Round {
    private final int totalMines;
    private final int totalTries;
    private final int totalTime;
    private final boolean userWon;

    public Round(int totalMines, int totalTries, int totalTime, boolean userWon) {
        this.totalMines = totalMines;
        this.totalTries = totalTries;
        this.totalTime = totalTime;
        this.userWon = userWon;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public int getTotalTries() {
        return totalTries;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean hasUserWon() {
        return userWon;
    }

    @Override
    public String toString() {
        return "Total mines: " + totalMines +
                "\nTotal tries: " + totalTries +
                "\nTotal time: " + totalTime +
                "\nWinner: " + (userWon ? "Player" : "Computer");
    }
}
