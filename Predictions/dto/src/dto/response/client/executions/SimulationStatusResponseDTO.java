package dto.response.client.executions;

import dto.DTO;

public class SimulationStatusResponseDTO implements DTO {
    private Integer simulationID;
    private Boolean isRunning;
    private Boolean isPaused;

    public Integer getSimulationID() {
        return simulationID;
    }

    public Boolean getRunning() {
        return isRunning;
    }

    public void setSimulationID(Integer simulationID) {
        this.simulationID = simulationID;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public void setPaused(Boolean paused) {
        isPaused = paused;
    }
}
