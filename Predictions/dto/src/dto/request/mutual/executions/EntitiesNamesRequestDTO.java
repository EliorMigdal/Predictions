package dto.request.mutual.executions;

import dto.DTO;

public class EntitiesNamesRequestDTO implements DTO {
    private final String simulationName;

    public EntitiesNamesRequestDTO(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
