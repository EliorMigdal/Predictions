package engine.simulation.time.type;

public class RunTime {
    private long startTime;
    private long runTime;

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public double getTimeProgress(PauseTime pauseTime) {
        this.runTime = ((System.currentTimeMillis() - this.startTime - pauseTime.getPauseDuration()) / 1000L);
        return (double) this.runTime;
    }

    public double getRunTime() {
        return (double) runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }
}
