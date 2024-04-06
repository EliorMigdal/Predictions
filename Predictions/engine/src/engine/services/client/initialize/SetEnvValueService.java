package engine.services.client.initialize;

import dto.request.client.init.SetEnvValueDTO;
import dto.response.client.init.SetEnvValueResponseDTO;
import engine.Engine;
import engine.services.Service;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.cast.GenerateBooleanFromString;
import engine.utility.function.type.cast.GenerateNumberFromString;

public class SetEnvValueService implements Service {
    public SetEnvValueResponseDTO setEnvironmentValue(SetEnvValueDTO requestDTO, Engine engine) {
        return engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream())
                        .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                        .flatMap(simulation -> simulation.getWorld().getEnvironmentVariables().getProperties().stream()
                                .filter(property -> property.getPropertyName().equals(requestDTO.getPropertyName()))))
                .map(property -> {
                    try {
                        if (requestDTO.getSetRandomly()) {
                            property.randomise();
                        } else {
                            Object boolRes = new GenerateBooleanFromString(requestDTO.getPropertyValue()).run();
                            Object numRes = new GenerateNumberFromString(requestDTO.getPropertyValue()).run();

                            if (boolRes != null) {
                                property.setPropertyValue(boolRes);
                            } else if (numRes != null) {
                                property.setPropertyValue(numRes);
                            } else {
                                property.setPropertyValue(requestDTO.getPropertyValue());
                            }
                        }
                        return new SetEnvValueResponseDTO(true, "Successfully set!");
                    } catch (InvalidEntity | InvalidPropertyType | InvalidPropertyReference | IncompatibleTypes |
                             ValueOutOfRange exception) {
                        return new SetEnvValueResponseDTO(false, exception.getMessage());
                    }
                }).findFirst().orElse(null);
    }
}
