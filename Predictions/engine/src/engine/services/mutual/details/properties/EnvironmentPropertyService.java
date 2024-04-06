package engine.services.mutual.details.properties;

import dto.request.mutual.details.properties.EnvironmentPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.Engine;
import engine.services.Service;

import java.util.Optional;

public class EnvironmentPropertyService implements Service {
    public PropertyResponseDTO provideService(EnvironmentPropertyRequestDTO requestDTO, Engine engine) {
        PropertyResponseDTO responseDTO = new PropertyResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().flatMap(simulation -> simulation.getPRDEnvironment().getPRDEnvProperty()
                        .stream().filter(envProp -> envProp.getPRDName().equals(requestDTO.getPropertyName()))
                        .findFirst()).ifPresent(envProp -> {
                    responseDTO.addProperty(new PropertyDTO("Property type:", envProp.getType()));
                    Optional.ofNullable(envProp.getPRDRange())
                            .ifPresent(range -> responseDTO.addProperty(new PropertyDTO("Property range:",
                                    range.getFrom() + " to " + range.getTo())));
                });

        return responseDTO;
    }
}
