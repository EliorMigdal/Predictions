package dto.request.client.init;

import dto.DTO;

public class SetPopulationDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;
    private final String entityName;
    private final Integer population;

    public SetPopulationDTO(String clientName, Integer requestID, Integer simulationID,
                            String entityName, Integer population) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationID = simulationID;
        this.entityName = entityName;
        this.population = population;
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

    public Integer getPopulation() {
        return population;
    }
}
