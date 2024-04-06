package dto.response.client.executions;

import dto.DTO;

public class ExecutionDTO implements DTO {
    private final Integer requestID;
    private final String simulationName;
    private final Integer simulationID;
    private final Boolean isRunning;

    public ExecutionDTO(Integer requestID, String simulationName, Integer simulationID, Boolean isRunning) {
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.simulationID = simulationID;
        this.isRunning = isRunning;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public Integer getSimulationID() {
        return simulationID;
    }

    public Boolean getRunning() {
        return isRunning;
    }
}
