package dto.response.admin.execution;

import dto.DTO;

import java.util.ArrayList;

public class ExecutedSimulationsResponseDTO implements DTO {
    private final ArrayList<ExecutedSimulationDTO> executedSimulations = new ArrayList<>();
    private Integer version = -1;

    public ArrayList<ExecutedSimulationDTO> getExecutedSimulations() {
        return executedSimulations;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void addExecutedItem(ExecutedSimulationDTO newItem) {
        executedSimulations.add(newItem);
    }
}
