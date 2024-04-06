package dto.response.client.init;

import dto.DTO;

public class NewExecutionDTO implements DTO {
    private Integer simulationID;

    public void setSimulationID(Integer simulationID) {
        this.simulationID = simulationID;
    }

    public Integer getSimulationID() {
        return simulationID;
    }
}
