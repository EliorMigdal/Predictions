package dto.request.mutual.details.properties;

import dto.DTO;

public class EnvironmentPropertyRequestDTO implements DTO {
    private final String simulationName;
    private final String propertyName;

    public EnvironmentPropertyRequestDTO(String simulationName, String propertyName) {
        this.simulationName = simulationName;
        this.propertyName = propertyName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
