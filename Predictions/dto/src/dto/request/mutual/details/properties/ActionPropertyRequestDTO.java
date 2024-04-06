package dto.request.mutual.details.properties;

import dto.DTO;

public class ActionPropertyRequestDTO implements DTO {
    private final String simulationName;
    private final String ruleName;
    private final String actionName;
    private final String actionIndex;

    public ActionPropertyRequestDTO(String simulationName, String ruleName, String actionName, String actionIndex) {
        this.simulationName = simulationName;
        this.ruleName = ruleName;
        this.actionName = actionName;
        this.actionIndex = actionIndex;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionIndex() {
        return actionIndex;
    }
}
