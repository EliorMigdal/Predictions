package engine.services.client.executions;

import dto.request.client.executions.RunSimulationDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.Engine;
import engine.services.Service;
import engine.version.types.usage.ClientUsageUpdate;

public class RunSimulationService implements Service {
    public EmptyResponseDTO runSimulation(RunSimulationDTO requestDTO, Engine engine) {
        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                        .findFirst().ifPresent(client -> {
                            client.nullifyCurrentlyInitialized();
                            client.getUsageData().incrementNumOfWaiting();
                            engine.getVersionManager().getUsageVersions().addNewUsageUpdate(
                                    new ClientUsageUpdate(client.getClientName(), client.getUsageData()));
                            client.getClientRequests().stream()
                                    .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                                    .findFirst().ifPresent(request -> {
                                        request.decrementRemainingExecutions();
                                        request.getSimulations().stream()
                                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                                                .findFirst().ifPresent(simulation -> engine.runSimulation(simulation, client));
                            });
                });

        return new EmptyResponseDTO();
    }
}
