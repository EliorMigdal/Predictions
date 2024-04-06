package dto.response.admin.execution;

public class ExecutedSimulationDTO {
    private final String clientName;
    private final Integer requestID;
    private final String simulationName;
    private final Integer simulationID;

    public ExecutedSimulationDTO(String clientName, Integer requestID,
                                 String simulationName, Integer simulationID) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.simulationID = simulationID;
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

    public Integer getSimulationID() {
        return simulationID;
    }
}
