package dto.request.client.init;

import dto.DTO;

public class SetEnvValueDTO implements DTO {
    private final String clientName;
    private final Integer requestID;
    private final Integer simulationID;
    private final String propertyName;
    private final String propertyValue;
    private final Boolean setRandomly;

    public SetEnvValueDTO(String clientName, Integer requestID, Integer simulationID,
                          String propertyName, String propertyValue, Boolean setRandomly) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationID = simulationID;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.setRandomly = setRandomly;
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

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public Boolean getSetRandomly() {
        return setRandomly;
    }
}
