package dto.response.admin.activity;

import dto.DTO;

public class CurrentlyRunningDTO implements DTO {
    private final String simulationName;
    private final String requestID;

    public CurrentlyRunningDTO(String simulationName, String requestID) {
        this.simulationName = simulationName;
        this.requestID = requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getRequestID() {
        return requestID;
    }
}
