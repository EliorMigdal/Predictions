package engine.services.client.requests;

import dto.request.client.requests.AllClientRequestsDTO;
import dto.response.client.requests.RequestDTO;
import dto.response.client.requests.RequestsListDTO;
import engine.Engine;
import engine.services.Service;

public class RequestsListService implements Service {
    public RequestsListDTO getRequestsList(AllClientRequestsDTO requestDTO, Engine engine) {
        RequestsListDTO requestsList = new RequestsListDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().ifPresent(client -> client.getClientRequests()
                        .forEach(request -> requestsList.addNewRequest(new RequestDTO(
                                request.getRequestID().toString(), request.getSimulationName(),
                                request.getTotal_RemainingAmount(), request.getRequestDate(),
                                request.getTerminationInfo(), request.getReqStatus()
                        ))));

        return requestsList;
    }
}
