package dto.request.mutual.executions;

import dto.DTO;

public class EntityPropertiesRequestDTO implements DTO {
    private final String simulationName;
    private final String entityName;

    public EntityPropertiesRequestDTO(String simulationName, String entityName) {
        this.simulationName = simulationName;
        this.entityName = entityName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getEntityName() {
        return entityName;
    }
}
