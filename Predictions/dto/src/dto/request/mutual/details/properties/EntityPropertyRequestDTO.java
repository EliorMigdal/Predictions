package dto.request.mutual.details.properties;

import dto.DTO;

public class EntityPropertyRequestDTO implements DTO {
    private final String simulationName;
    private final String entityName;
    private final String propertyName;

    public EntityPropertyRequestDTO(String simulationName, String entityName, String propertyName) {
        this.simulationName = simulationName;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
