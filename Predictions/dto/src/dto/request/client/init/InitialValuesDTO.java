package dto.request.client.init;

import dto.DTO;

public class InitialValuesDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;

    public InitialValuesDTO(String clientName, Integer requestID, Integer simulationID) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationID = simulationID;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public Integer getSimulationID() {
        return simulationID;
    }
}
