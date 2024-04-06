package engine.services.mutual.executions;

import dto.request.mutual.executions.EntityPropertiesRequestDTO;
import dto.response.mutual.executions.EntityPropertiesResponseDTO;
import engine.Engine;
import engine.services.Service;

public class EntityPropertiesService implements Service {
    public EntityPropertiesResponseDTO getEntityProperties(EntityPropertiesRequestDTO requestDTO, Engine engine) {
        EntityPropertiesResponseDTO responseDTO = new EntityPropertiesResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .flatMap(simulation -> simulation.getPRDEntities().getPRDEntity().stream()
                        .filter(entity -> entity.getName().equals(requestDTO.getEntityName()))
                        .flatMap(entity -> entity.getPRDProperties().getPRDProperty().stream()))
                .forEach(property -> responseDTO.addPropertyName(property.getPRDName()));

        return responseDTO;
    }
}
