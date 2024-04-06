package dto.response.admin.general;

import dto.DTO;

public class RequestsSizeResponseDTO implements DTO {
    private final Integer numOfRequests;

    public RequestsSizeResponseDTO(Integer numOfRequests) {
        this.numOfRequests = numOfRequests;
    }

    public Integer getNumOfRequests() {
        return numOfRequests;
    }
}
