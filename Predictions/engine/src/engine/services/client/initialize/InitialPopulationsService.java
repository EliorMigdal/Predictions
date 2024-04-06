package engine.services.client.initialize;

import dto.request.client.init.InitialValuesDTO;
import dto.response.client.init.InitialPopulationItemDTO;
import dto.response.client.init.InitialPopulationResponseDTO;
import engine.Engine;
import engine.services.Service;

public class InitialPopulationsService implements Service {
    public InitialPopulationResponseDTO getInitialPopulation(InitialValuesDTO requestDTO, Engine engine) {
        InitialPopulationResponseDTO responseDTO = new InitialPopulationResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))))
                .findFirst().ifPresent(simulation ->
                        simulation.getWorld().getEntities()
                                .forEach(entity -> responseDTO.addPopulationItem
                                        (new InitialPopulationItemDTO(entity.getEntityName(),
                                                entity.getEntityPopulation()))));

        return responseDTO;
    }
}
