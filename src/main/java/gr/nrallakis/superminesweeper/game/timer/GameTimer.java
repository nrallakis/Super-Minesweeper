package gr.nrallakis.superminesweeper.game.timer;

public interface GameTimer {
    void start();
    void stop();
    void setOnTimeoutListener(TimeoutListener listener);
    void setTimeChangedListener(TimeChangedListener listener);
    int getTimeLeft();
}

