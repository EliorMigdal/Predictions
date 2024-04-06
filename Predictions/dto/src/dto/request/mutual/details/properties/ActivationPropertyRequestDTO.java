package dto.request.mutual.details.properties;

import dto.DTO;

public class ActivationPropertyRequestDTO implements DTO {
    private final String simulationName;
    private final String ruleName;
    private final String activationName;

    public ActivationPropertyRequestDTO(String simulationName, String ruleName, String activationName) {
        this.simulationName = simulationName;
        this.ruleName = ruleName;
        this.activationName = activationName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getActivationName() {
        return activationName;
    }
}
