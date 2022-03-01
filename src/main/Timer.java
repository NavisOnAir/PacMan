package main;

public class Timer {
    // time variables
    private long nanoSeconds; // current time in nanoseconds
    private double seconds; // current time in seconds
    private long startTime; // time when the timer was started
    private boolean isStopped;
    private long stoppedTime; // time when the timer was paused
    private final long nanoToSecondsConstant = 1000000000;

    // start timer
    public void start() {
        isStopped = false;
        startTime = System.nanoTime();
    }

    // resumes timer
    public void resume() {
        isStopped = false;
        startTime = System.nanoTime() - (stoppedTime - startTime);
    }

    // pause timer
    public void pause() {
        stoppedTime = System.nanoTime();
        isStopped = true;
    }

    // return time in seconds
    public double getTimeInSeconds() {
        if (isStopped) {
            return (stoppedTime - startTime) / nanoToSecondsConstant;
        } else {
            return (System.nanoTime() - startTime) / nanoToSecondsConstant;
        }
    }

    // return time in nanoseconds
    public long getTimeInNanoSeconds() {
        if (isStopped) {
            return stoppedTime - startTime;
        } else {
            return System.nanoTime() - startTime;
        }
    }

    // reset and stop timer
    public void reset() {
        this.startTime = System.nanoTime();
        isStopped = true;
    }

    // return if timer is paused
    public boolean getStopped() {
        return isStopped;
    }
}
