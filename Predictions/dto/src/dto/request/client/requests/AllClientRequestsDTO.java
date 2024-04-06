package dto.request.client.requests;

import dto.DTO;

public class AllClientRequestsDTO implements DTO {
    private final String clientName;

    public AllClientRequestsDTO(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
