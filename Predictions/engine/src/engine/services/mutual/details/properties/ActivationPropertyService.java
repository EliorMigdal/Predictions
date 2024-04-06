package engine.services.mutual.details.properties;

import dto.request.mutual.details.properties.ActivationPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.Engine;
import engine.jaxb.generated.PRDActivation;
import engine.services.Service;

import java.util.Optional;

public class ActivationPropertyService implements Service {
    public PropertyResponseDTO provideService(ActivationPropertyRequestDTO requestDTO, Engine engine) {
        PropertyResponseDTO responseDTO = new PropertyResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().flatMap(simulation -> simulation.getPRDRules().getPRDRule()
                        .stream().filter(rule -> rule.getName().equals(requestDTO.getRuleName()))
                        .findFirst()).ifPresent(rule -> {
                    switch (requestDTO.getActivationName()) {
                        case "Ticks":
                            Optional<Integer> optionalTicks = Optional.ofNullable(rule.getPRDActivation())
                                    .map(PRDActivation::getTicks);

                            if (optionalTicks.isPresent()) {
                                responseDTO.addProperty(new PropertyDTO("Ticks:",
                                        optionalTicks.get().toString()));
                            } else {
                                responseDTO.addProperty(new PropertyDTO("Ticks:", "1"));
                            }
                            break;
                        case "Probability":
                            Optional<Double> optionalProbability = Optional.ofNullable(rule.getPRDActivation())
                                    .map(PRDActivation::getProbability);

                            if (optionalProbability.isPresent()) {
                                responseDTO.addProperty(new PropertyDTO("Probability:",
                                        optionalProbability.get().toString()));
                            } else {
                                responseDTO.addProperty(new PropertyDTO("Probability:",
                                        "1.0"));
                            }
                            break;
                        default:
                            break;
                    }
                });

        return responseDTO;
    }
}
