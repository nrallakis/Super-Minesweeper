package gr.nrallakis.superminesweeper.game.timer;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownTimer implements GameTimer {
    private int timeLeft;
    private final Timer timer;
    private TimeoutListener timeoutListener;
    private TimeChangedListener timeChangedListener;

    public CountDownTimer(int totalTime) {
        this.timeLeft = totalTime;
        this.timer = new Timer();
    }

    @Override
    public void start() {
        int oneSecond = 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                if (timeChangedListener != null) {
                    timeChangedListener.onTimeChanged(timeLeft);
                }
                if (timeLeft == 0) {
                    timeoutListener.onTimeout();
                    stop();
                }
            }
        }, 0, oneSecond);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    @Override
    public void setOnTimeoutListener(TimeoutListener listener) {
        this.timeoutListener = listener;
    }

    @Override
    public int getTimeLeft() {
        return timeLeft;
    }

    @Override
    public void setTimeChangedListener(TimeChangedListener listener) {
        this.timeChangedListener = listener;
    }
}
