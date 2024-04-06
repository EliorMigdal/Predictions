package engine.service.client.user.type;

import engine.EngineService;
import engine.client.Client;
import dto.request.client.ClientRequestsDTO;
import engine.service.client.user.UserRequest;
import engine.service.exception.InitException;

import java.util.ArrayList;
import java.util.Optional;

public class ViewAllRequests implements UserRequest {
    private EngineService engine;


    public ViewAllRequests() {
    }

    public ViewAllRequests(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws InitException {
        String clientName = (String) args[0];
        Optional<Client> optionalClient = engine.getClientData(clientName);
        ArrayList<ClientRequestsDTO> requestsInfo = new ArrayList<>();

        optionalClient.ifPresent(clientData -> clientData.getClientRequests()
                .forEach(request -> requestsInfo.add(
                        new ClientRequestsDTO(
                                String.valueOf(request.getRequestID()),
                                request.getSimulationName(),
                                request.getTotal_RemainingAmount(),
                                request.getRequestDate(),
                                request.getTerminationInfo(),
                                request.getReqStatus()
                        )
                )));
        return requestsInfo;
    }
}
