package engine.services.client.initialize;

import dto.request.client.init.SetPopulationDTO;
import dto.response.client.init.SetPopulationResponseDTO;
import engine.Engine;
import engine.services.Service;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.grid.exception.NotEnoughSpace;

public class SetPopulationService implements Service {
    public SetPopulationResponseDTO setPopulation(SetPopulationDTO requestDTO, Engine engine) {
        return engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream())
                        .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                        .flatMap(simulation -> simulation.getWorld().getEntities().stream()
                                .filter(entity -> entity.getEntityName().equals(requestDTO.getEntityName()))))
                .map(entity -> {
                    try {
                        entity.initializePopulation(requestDTO.getPopulation());
                        return new SetPopulationResponseDTO(true, "Successfully set!");
                    } catch (InvalidPopulation | NotEnoughSpace exception) {
                        return new SetPopulationResponseDTO(false, exception.getMessage());
                    }
                }).findFirst().orElse(null);
    }
}
