package dto.response.admin.activity;

import dto.DTO;

public class ThreadPoolStatusResponseDTO implements DTO {
    private String queueSize;
    private String currentlyRunning;
    private String finished;

    public String getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(String queueSize) {
        this.queueSize = queueSize;
    }

    public String getCurrentlyRunning() {
        return currentlyRunning;
    }

    public void setCurrentlyRunning(String currentlyRunning) {
        this.currentlyRunning = currentlyRunning;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }
}
