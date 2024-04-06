package dto.response.admin.activity;

import dto.DTO;

public class ClientUsageResponseOldDTO implements DTO {
    private final String clientName;
    private String currentlyRunning;
    private String currentlyQueued;
    private String currentlyFinished;

    public ClientUsageResponseOldDTO(String clientName) {
        this.clientName = clientName;
    }

    public void setCurrentlyRunning(String currentlyRunning) {
        this.currentlyRunning = currentlyRunning;
    }

    public void setCurrentlyQueued(String currentlyQueued) {
        this.currentlyQueued = currentlyQueued;
    }

    public void setCurrentlyFinished(String currentlyFinished) {
        this.currentlyFinished = currentlyFinished;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCurrentlyRunning() {
        return currentlyRunning;
    }

    public String getCurrentlyQueued() {
        return currentlyQueued;
    }

    public String getCurrentlyFinished() {
        return currentlyFinished;
    }
}
