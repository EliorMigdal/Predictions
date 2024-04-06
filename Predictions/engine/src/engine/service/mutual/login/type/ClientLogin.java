package engine.service.mutual.login.type;

import engine.EngineService;
import engine.client.Client;
import engine.manager.exception.UserAlreadyExists;
import engine.service.mutual.login.LoginRequest;
import engine.version.types.usage.ClientUsageUpdate;

public class ClientLogin implements LoginRequest {
    private EngineService engine;

    public ClientLogin() {
    }

    public ClientLogin(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws UserAlreadyExists {
        String clientName = (String) args[0];
        engine.getUserManager().addNewClient(clientName);

        boolean foundClient = engine.getClients().stream().anyMatch(client -> client.getClientName().equals(clientName));
        if (!foundClient) {
            Client newClient = new Client(clientName);
            engine.getClients().add(newClient);
            engine.getVersionManager().addNewUsageData(new ClientUsageUpdate(clientName, newClient.getUsageData()));
            engine.getVersionManager().addNewClient(clientName);
        }

        return true;
    }
}
