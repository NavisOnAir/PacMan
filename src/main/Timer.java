package main;

public class Timer {
    // time variables
    private long nanoSeconds;
    private double seconds;
    private long startTime = 0;
    private boolean isStopped;

    // start timer
    public void start() {
        this.isStopped = false;
        startTime = System.nanoTime();
    }

    // pause timer
    public void pause() {
        this.isStopped = true;
        long currentTime = System.nanoTime();
        this.seconds += (currentTime - this.startTime) / 1000000000;
        this.nanoSeconds += (currentTime - this.startTime);
    }

    // return time in seconds
    public double getTimeInSeconds() {
        long currentTime = System.nanoTime();
        if (isStopped) {
            return this.seconds;
        } else {
            return this.seconds + (currentTime - this.startTime) / 1000000000;
        }
    }

    // return time in nanoseconds
    public long getTimeInNanoSeconds() {
        long currentTime = System.nanoTime();
        if (isStopped) {
            return this.nanoSeconds;
        } else {
            return this.nanoSeconds + (currentTime - this.startTime);
        }
    }

    // reset timer
    public void reset() {
        this.startTime = System.nanoTime();
    }

    // return if timer is paused
    public boolean getStopped() {
        return isStopped;
    }
}
