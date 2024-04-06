package dto.request.admin.activity;

import dto.DTO;

public class CurrentlyRunningRequestDTO implements DTO {
    private final String clientName;

    public CurrentlyRunningRequestDTO(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
