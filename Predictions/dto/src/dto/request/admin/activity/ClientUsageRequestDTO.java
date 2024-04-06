package dto.request.admin.activity;

import dto.DTO;

public class ClientUsageRequestDTO implements DTO {
    private final Integer version;

    public ClientUsageRequestDTO(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
