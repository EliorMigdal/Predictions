package engine.services.client.initialize;

import dto.request.client.init.EnvironmentPropertiesDTO;
import dto.response.client.init.AllEnvPropertiesDTO;
import dto.response.client.init.EnvPropertyDTO;
import engine.Engine;
import engine.services.Service;

import java.util.Optional;

public class EnvironmentPropertiesService implements Service {
    public AllEnvPropertiesDTO getEnvironmentProperties(EnvironmentPropertiesDTO requestDTO, Engine engine) {
        AllEnvPropertiesDTO envProperties = new AllEnvPropertiesDTO();
        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().ifPresent(simulation -> simulation.getPRDEnvironment().getPRDEnvProperty()
                        .forEach(property -> {
                            EnvPropertyDTO newProperty = new EnvPropertyDTO(property.getPRDName(),
                                    property.getType());

                            Optional.ofNullable(property.getPRDRange()).ifPresent(range ->
                                    newProperty.setPropertyRange(range.getFrom() + " to " + range.getTo()));

                            envProperties.addNewItem(newProperty);
                        }));

        return envProperties;
    }
}
