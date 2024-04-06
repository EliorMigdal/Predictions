package dto.request.admin.general;

import dto.DTO;

public class RequestsSizeRequestDTO implements DTO {
    private final String clientName;

    public RequestsSizeRequestDTO(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
