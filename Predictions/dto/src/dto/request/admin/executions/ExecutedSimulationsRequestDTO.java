package dto.request.admin.executions;

import dto.DTO;

public class ExecutedSimulationsRequestDTO implements DTO {
    private final Integer version;

    public ExecutedSimulationsRequestDTO(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
