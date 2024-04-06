package engine.services.mutual.details.properties;

import dto.request.mutual.details.properties.EntityPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.Engine;
import engine.services.Service;

import java.util.Optional;

public class EntityPropertyService implements Service {
    public PropertyResponseDTO provideService(EntityPropertyRequestDTO requestDTO, Engine engine) {
        PropertyResponseDTO responseDTO = new PropertyResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().flatMap(simulation -> simulation.getPRDEntities().getPRDEntity()
                        .stream().filter(entity -> entity.getName().equals(requestDTO.getEntityName()))
                        .findFirst()).flatMap(entity -> entity.getPRDProperties().getPRDProperty().stream()
                        .filter(property -> property.getPRDName().equals(requestDTO.getPropertyName()))
                        .findFirst()).ifPresent(property -> {
                    responseDTO.addProperty(new PropertyDTO("Property type:", property.getType()));
                    Optional.ofNullable(property.getPRDRange())
                            .ifPresent(range ->
                                    responseDTO.addProperty(new PropertyDTO("Property range:",
                                            range.getFrom() + " to " + range.getTo())));
                    responseDTO.addProperty(new PropertyDTO("Random init:",
                            ((Boolean) property.getPRDValue().isRandomInitialize()).toString()));
                });

        return responseDTO;
    }
}
