package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;
import engine.simulation.Simulation;
import dto.response.admin.activity.ClientUsageResponseOldDTO;

import java.util.ArrayList;

public class GetClientsUsage implements ThreadPoolRequest {
    private EngineService engine;

    public GetClientsUsage() {
    }

    public GetClientsUsage(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        ArrayList<ClientUsageResponseOldDTO> clientsUsage = new ArrayList<>();

        engine.getClients().forEach(client -> {
            ClientUsageResponseOldDTO currentClient = new ClientUsageResponseOldDTO(client.getClientName());
            long numOfQueued = client.getClientRequests().stream()
                    .flatMap(clientRequestData -> clientRequestData.getSimulations().stream())
                    .filter(Simulation::getQueueStatus)
                    .count();

            long numOfRunning = client.getClientRequests().stream()
                    .flatMap(clientRequestData -> clientRequestData.getSimulations().stream())
                    .filter(simulation -> simulation.hasStartedRunning() && !simulation.getFinishStatus())
                    .count();

            long numOfFinished = client.getClientRequests().stream()
                    .flatMap(clientRequestData -> clientRequestData.getSimulations().stream())
                    .filter(Simulation::getFinishStatus)
                    .count();

            currentClient.setCurrentlyFinished(Long.toString(numOfFinished));
            currentClient.setCurrentlyQueued(Long.toString(numOfQueued));
            currentClient.setCurrentlyRunning(Long.toString(numOfRunning));

            clientsUsage.add(currentClient);
        });

        return clientsUsage;
    }
}
