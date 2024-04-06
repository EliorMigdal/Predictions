package dto.request.mutual.executions;

import dto.DTO;

public class PopulationResultsRequestDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;

    public PopulationResultsRequestDTO(String clientName, Integer requestID, Integer simulationID) {
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
