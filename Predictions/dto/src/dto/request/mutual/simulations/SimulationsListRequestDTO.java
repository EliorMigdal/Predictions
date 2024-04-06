package dto.request.mutual.simulations;

import dto.DTO;

public class SimulationsListRequestDTO implements DTO {
    private final Integer version;

    public SimulationsListRequestDTO(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
