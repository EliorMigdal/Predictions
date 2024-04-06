package dto.response.mutual.simulations;

import dto.DTO;

import java.util.ArrayList;

public class SimulationsListResponseDTO implements DTO {
    private final ArrayList<SimulationInfoDTO> simulationsList = new ArrayList<>();
    private Integer version = -1;

    public ArrayList<SimulationInfoDTO> getSimulationsList() {
        return simulationsList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void addNewSimulation(SimulationInfoDTO simulationInfoDTO) {
        this.simulationsList.add(simulationInfoDTO);
    }
}
