package engine.version;

import engine.client.request.Request;
import engine.jaxb.generated.PRDWorld;
import engine.version.types.clients.ClientsVersions;
import engine.version.types.executions.ExecutionItem;
import engine.version.types.executions.ExecutionsVersions;
import engine.version.types.requests.RequestsVersions;
import engine.version.types.simulations.SimulationsVersions;
import engine.version.types.usage.ClientUsageUpdate;
import engine.version.types.usage.ClientUsageVersions;

import java.time.LocalDate;
import java.util.Map;

public class VersionManager {
    private final ClientsVersions clientsVersions = new ClientsVersions();
    private final SimulationsVersions simulationsVersions = new SimulationsVersions();
    private final ClientUsageVersions usageVersions = new ClientUsageVersions();
    private final RequestsVersions requestsVersions = new RequestsVersions();
    private final ExecutionsVersions executionsVersions = new ExecutionsVersions();

    public void addNewSimulation(PRDWorld world, LocalDate date) {
        simulationsVersions.addNewSimulation(world, date);
    }

    public SimulationsVersions getSimulationsVersions() {
        return simulationsVersions;
    }

    synchronized public void addNewUsageData(ClientUsageUpdate usageUpdate) {
        usageVersions.addNewUsageUpdate(usageUpdate);
    }

    public ClientUsageVersions getUsageVersions() {
        return usageVersions;
    }

    public void addNewRequest(Request newRequest) {
        requestsVersions.addNewRequest(newRequest);
    }

    public RequestsVersions getRequestsVersions() {
        return requestsVersions;
    }

    public Map<Integer, String> getClientsVersions() {
        return clientsVersions.getClientsNames();
    }

    public void addNewClient(String clientName) {
        clientsVersions.addNewClient(clientName);
    }

    public ExecutionsVersions getExecutionsVersions() {
        return executionsVersions;
    }

    public void addNewExecutionItem(ExecutionItem newItem) {
        executionsVersions.addExecutionItem(newItem);
    }
}
