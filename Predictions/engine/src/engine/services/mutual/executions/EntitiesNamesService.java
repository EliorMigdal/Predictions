package engine.services.mutual.executions;

import dto.request.mutual.executions.EntitiesNamesRequestDTO;
import dto.response.mutual.executions.EntitiesNamesDTO;
import engine.Engine;
import engine.services.Service;

public class EntitiesNamesService implements Service {
    public EntitiesNamesDTO getEntityNames(EntitiesNamesRequestDTO requestDTO, Engine engine) {
        EntitiesNamesDTO responseDTO = new EntitiesNamesDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().ifPresent(simulation -> simulation.getPRDEntities().getPRDEntity()
                        .forEach(entity -> responseDTO.addNewEntity(entity.getName())));

        return responseDTO;
    }
}
