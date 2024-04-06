package dto.response.mutual.simulations;

import dto.DTO;

import java.time.LocalDate;

public class SimulationInfoDTO implements DTO {
    private final String simulationName;
    private final LocalDate addedDate;

    public SimulationInfoDTO(String simulationName, LocalDate addedDate) {
        this.simulationName = simulationName;
        this.addedDate = addedDate;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }
}
