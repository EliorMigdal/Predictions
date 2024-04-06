package engine.service.client.user.type;

import engine.EngineService;
import engine.client.request.Request;
import engine.service.client.user.UserRequest;
import engine.service.exception.InitException;
import dto.request.client.requests.TerminationInfoDTO;

import java.util.ArrayList;

public class CreateNewRequest implements UserRequest {
    private EngineService engine;

    public CreateNewRequest() {
    }

    public CreateNewRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args)
            throws InitException {
        String clientName = (String) args[0];
        String simulationName = (String) args[1];
        String numOfExecutions = (String) args[2];
        ArrayList<TerminationInfoDTO> terminations = (ArrayList<TerminationInfoDTO> ) args[3];

        engine.getClients().stream().filter(client -> client.getClientName().equals(clientName))
                .findFirst().ifPresent(client -> {
                    Request newRequest = new Request(client.getClientName(),
                            client.getClientRequests().size() + 1, numOfExecutions, simulationName,
                            terminations);
                    client.addNewRequest(newRequest);
                    engine.getVersionManager().addNewRequest(newRequest);
                });

        return true;
    }
}
