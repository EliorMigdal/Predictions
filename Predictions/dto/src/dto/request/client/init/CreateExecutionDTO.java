package dto.request.client.init;

import dto.DTO;

public class CreateExecutionDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final String simulationName;

    public CreateExecutionDTO(String clientName, Integer requestID, String simulationName) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationName = simulationName;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
