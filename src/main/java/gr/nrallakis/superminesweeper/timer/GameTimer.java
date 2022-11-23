package gr.nrallakis.superminesweeper.timer;

public interface GameTimer {
    void start();
    void stop();

    void setOnTimeoutListener(TimeoutListener listener);
    void setTimeChangedListener(TimeChangedListener listener);
}

