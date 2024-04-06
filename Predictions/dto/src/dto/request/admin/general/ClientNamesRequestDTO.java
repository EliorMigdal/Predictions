package dto.request.admin.general;

import dto.DTO;

public class ClientNamesRequestDTO implements DTO {
    private final Integer version;

    public ClientNamesRequestDTO(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
