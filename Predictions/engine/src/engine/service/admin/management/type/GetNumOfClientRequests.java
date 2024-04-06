package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;

import java.util.OptionalInt;

public class GetNumOfClientRequests implements ManagementRequest {
    private EngineService engine;

    public GetNumOfClientRequests() {
    }

    public GetNumOfClientRequests(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws XMLException, InitException {
        String clientName = args[0].toString();

        OptionalInt requestSize = engine.getClients().stream()
                .filter(client -> client.getClientName().equals(clientName))
                .mapToInt(client -> client.getClientRequests().size())
                .findFirst();

        if (requestSize.isPresent()) {
            return ((Integer) requestSize.getAsInt()).toString();
        } else {
            return "-1";
        }
    }
}
