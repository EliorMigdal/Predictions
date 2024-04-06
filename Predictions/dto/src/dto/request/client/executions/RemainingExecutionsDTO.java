package dto.request.client.executions;

import dto.DTO;

public class RemainingExecutionsDTO implements DTO {
    private final String clientName;
    private final Integer requestID;

    public RemainingExecutionsDTO(String clientName, Integer requestID) {
        this.clientName = clientName;
        this.requestID = requestID;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getRequestID() {
        return requestID;
    }
}
