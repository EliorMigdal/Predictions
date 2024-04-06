package dto.request.mutual.executions;

import dto.DTO;

public class PropertyResultsRequestDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;
    private final String entityName;
    private final String propertyName;

    public PropertyResultsRequestDTO(String clientName, Integer requestID, Integer simulationID,
                                     String entityName, String propertyName) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationID = simulationID;
        this.entityName = entityName;
        this.propertyName = propertyName;
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

    public String getEntityName() {
        return entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
