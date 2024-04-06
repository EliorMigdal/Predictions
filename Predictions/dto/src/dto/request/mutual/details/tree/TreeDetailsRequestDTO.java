package dto.request.mutual.details.tree;

import dto.DTO;

public class TreeDetailsRequestDTO implements DTO {
    private final String simulationName;

    public TreeDetailsRequestDTO(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationName() {
        return simulationName;
    }
}
