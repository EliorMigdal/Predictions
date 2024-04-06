package dto.request.client.executions;

import dto.DTO;

public class AllExecutionsDTO implements DTO {
    private final String clientName;
    private final Integer version;

    public AllExecutionsDTO(String clientName, Integer version) {
        this.clientName = clientName;
        this.version = version;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getVersion() {
        return version;
    }
}
