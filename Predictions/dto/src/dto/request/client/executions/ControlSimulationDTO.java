package dto.request.client.executions;

import dto.DTO;
import dto.request.client.executions.enums.ControlOptions;

public class ControlSimulationDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;
    private final ControlOptions controlOption;

    public ControlSimulationDTO(String clientName, Integer requestID, Integer simulationID, ControlOptions controlOption) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationID = simulationID;
        this.controlOption = controlOption;
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

    public ControlOptions getControlOption() {
        return controlOption;
    }
}
