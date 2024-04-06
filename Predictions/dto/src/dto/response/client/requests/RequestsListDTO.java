package dto.response.client.requests;

import java.util.ArrayList;

public class RequestsListDTO {
    private final ArrayList<RequestDTO> requests = new ArrayList<>();

    public ArrayList<RequestDTO> getRequests() {
        return requests;
    }

    public void addNewRequest(RequestDTO newRequest) {
        this.requests.add(newRequest);
    }
}
