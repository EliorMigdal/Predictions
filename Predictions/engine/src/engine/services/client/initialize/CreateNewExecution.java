package engine.services.client.initialize;

import dto.request.client.init.CreateExecutionDTO;
import dto.response.client.init.NewExecutionDTO;
import engine.Engine;
import engine.services.Service;

public class CreateNewExecution implements Service {
    public NewExecutionDTO createExecution(CreateExecutionDTO requestDTO, Engine engine) {
        NewExecutionDTO response = new NewExecutionDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().ifPresent(client -> {
                    Integer simulationID = client.createNewSimulation(
                            engine.getSimulationsCollection().keySet().stream()
                                    .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                                    .findFirst().orElse(null), requestDTO.getRequestID());
                    response.setSimulationID(simulationID);
                });

        return response;
    }
}
