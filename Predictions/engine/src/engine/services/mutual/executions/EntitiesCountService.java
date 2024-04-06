package engine.services.mutual.executions;

import dto.request.mutual.executions.EntitiesCountRequestDTO;
import dto.response.mutual.executions.EntitiesCountResponseDTO;
import dto.response.mutual.executions.EntityCountDTO;
import engine.Engine;
import engine.services.Service;

public class EntitiesCountService implements Service {
    public EntitiesCountResponseDTO getEntitiesCount(EntitiesCountRequestDTO requestDTO, Engine engine) {
        EntitiesCountResponseDTO responseDTO = new EntitiesCountResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .findFirst().flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                                .findFirst())).ifPresent(simulation -> simulation.getWorld().getEntities()
                        .forEach(entity -> responseDTO.addNewEntityCount(
                                new EntityCountDTO(entity.getEntityName(), entity.getEntityPopulation())
                        )));

        return responseDTO;
    }
}
