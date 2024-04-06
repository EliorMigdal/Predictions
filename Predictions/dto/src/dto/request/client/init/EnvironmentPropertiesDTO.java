package dto.request.client.init;

import dto.DTO;

public class EnvironmentPropertiesDTO implements DTO {
    private final String simulationName;

    public EnvironmentPropertiesDTO(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
