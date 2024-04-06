package dto.response.admin.allocations;

import dto.DTO;

import java.util.ArrayList;

public class RequestsInfoDTO implements DTO {
    private final ArrayList<RequestDTO> requests = new ArrayList<>();
    private Integer version;

    public ArrayList<RequestDTO> getRequests() {
        return requests;
    }

    public void addNewItem(RequestDTO newRequest) {
        requests.add(newRequest);
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
