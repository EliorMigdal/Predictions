package engine.simulation.time.type;

public class PauseTime {
    private long pauseTime = 0L;
    private long pauseDuration = 0L;

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public void setCurrentPauseEndTime(long currentPauseEndTime) {
        this.pauseDuration += currentPauseEndTime - this.pauseTime;
    }
    public long getPauseDuration() {
        return pauseDuration;
    }
}
