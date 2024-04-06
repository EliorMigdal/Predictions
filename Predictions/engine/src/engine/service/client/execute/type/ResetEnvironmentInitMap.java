package engine.service.client.execute.type;

import engine.EngineService;
import engine.client.Client;
import engine.client.request.Request;
import engine.service.client.execute.SimulationExecuteRequest;
import engine.simulation.Simulation;

import java.util.Optional;

public class ResetEnvironmentInitMap implements SimulationExecuteRequest {
    private EngineService engine;

    public ResetEnvironmentInitMap() {
    }

    public ResetEnvironmentInitMap(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Client> optionalClient = engine.getClients().stream()
                .filter(clientData -> clientData.getClientName().equals(clientName))
                .findFirst();

        optionalClient.ifPresent(clientData -> {
            Optional<Request> optionalRequest = clientData.getClientRequests().stream()
                    .filter(requestData -> requestData.getRequestID().equals(requestID))
                    .findFirst();

            optionalRequest.ifPresent(requestData -> {
                Optional<Simulation> optionalSimulation = requestData.getSimulations().stream()
                        .filter(simulation -> simulation.getSimulationID().equals(simulationID))
                        .findFirst();

                optionalSimulation.ifPresent(simulation -> simulation.getWorld()
                        .getEnvironmentVariables().resetInitMap());
            });
        });

        return null;
    }
}
