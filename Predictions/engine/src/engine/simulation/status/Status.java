package engine.simulation.status;

public class Status {
    private Integer currentTick = 0;

    private volatile Boolean pauseStatus = false;
    private volatile Boolean stopStatus = false;

    private volatile Boolean manualProgressRequested = false;
    private volatile Boolean cancelManualProgress = false;

    private Boolean queueStatus = false;
    private Boolean finishStatus = false;
    private Boolean runStatus = false;

    private String stopReason = "";

    public Integer getCurrentTick() {
        return currentTick;
    }

    public Boolean getPauseStatus() {
        return pauseStatus;
    }

    public void setPauseStatus(Boolean pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public Boolean getStopStatus() {
        return stopStatus;
    }

    public void setStopStatus(Boolean stopStatus) {
        this.stopStatus = stopStatus;
    }

    public boolean isManualProgressRequested() {
        return manualProgressRequested;
    }

    public void setManualProgressRequest(boolean manualProgressRequested) {
        this.manualProgressRequested = manualProgressRequested;
    }

    public boolean toCancelManualProgress() {
        return cancelManualProgress;
    }

    public void setCancelManualProgress(boolean cancelManualProgress) {
        this.cancelManualProgress = cancelManualProgress;
    }

    public void incrementTick() {
        this.currentTick++;
    }

    public void setFinishStatus(Boolean finishStatus) {
        this.finishStatus = finishStatus;
    }

    public void setQueueStatus(Boolean queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public Boolean hasStartedRunning() {
        return runStatus;
    }

    public void setRunStatus(Boolean runStatus) {
        this.runStatus = runStatus;
    }

    public Boolean getFinishStatus() {
        return finishStatus;
    }

    public Boolean getQueueStatus() {
        return queueStatus;
    }
}
