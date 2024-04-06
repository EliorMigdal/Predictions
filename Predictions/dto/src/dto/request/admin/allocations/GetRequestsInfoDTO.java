package dto.request.admin.allocations;

import dto.DTO;

public class GetRequestsInfoDTO implements DTO {
    private final Integer version;

    public GetRequestsInfoDTO(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
