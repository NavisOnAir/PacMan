package main;

public class Timer {
    private long nanoSeconds;
    private double seconds;
    private long startTime = 0;
    private boolean isStopped;

    public void start() {
        this.isStopped = false;
        startTime = System.nanoTime();
    }

    public void pause() {
        this.isStopped = true;
        long currentTime = System.nanoTime();
        this.seconds += (currentTime - this.startTime) / 1000000000;
        this.nanoSeconds += (currentTime - this.startTime);
    }

    public double getTimeInSeconds() {
        long currentTime = System.nanoTime();
        if (isStopped) {
            return this.seconds;
        } else {
            return this.seconds + (currentTime - this.startTime) / 1000000000;
        }
    }

    public long getTimeInNanoSeconds() {
        long currentTime = System.nanoTime();
        if (isStopped) {
            return this.nanoSeconds;
        } else {
            return this.nanoSeconds + (currentTime - this.startTime);
        }
    }

    public void reset() {
        this.startTime = 0;
    }
}
