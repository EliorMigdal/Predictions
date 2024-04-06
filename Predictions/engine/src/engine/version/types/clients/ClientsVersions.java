package engine.version.types.clients;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientsVersions {
    private final Map<Integer, String> clientsNames = new LinkedHashMap<>();
    private Integer latestVersion = 0;

    public Map<Integer, String> getClientsNames() {
        return clientsNames;
    }

    public void addNewClient(String clientName) {
        clientsNames.put(++latestVersion, clientName);
    }
}
