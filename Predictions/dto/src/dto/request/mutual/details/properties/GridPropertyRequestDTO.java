package dto.request.mutual.details.properties;

import dto.DTO;

public class GridPropertyRequestDTO implements DTO {
    private final String simulationName;

    public GridPropertyRequestDTO(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
