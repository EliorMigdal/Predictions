package dto.request.admin.allocations;

import dto.DTO;

public class ApproveRequestDTO implements DTO {
    private final String clientName;
    private final Integer requestID;

    public ApproveRequestDTO(String clientName, Integer requestID) {
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
