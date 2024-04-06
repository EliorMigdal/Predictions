package engine.services.client.initialize;

import dto.request.client.init.InitialValuesDTO;
import dto.response.client.init.InitialEnvironmentItem;
import dto.response.client.init.InitialEnvironmentsResponseDTO;
import engine.Engine;
import engine.services.Service;

public class InitialEnvironmentsService implements Service {
    public InitialEnvironmentsResponseDTO getInitialEnvironments(InitialValuesDTO requestDTO, Engine engine) {
        InitialEnvironmentsResponseDTO responseDTO = new InitialEnvironmentsResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))))
                .findFirst().ifPresent(simulation ->
                        simulation.getWorld().getEnvironmentVariables().getProperties()
                                .forEach(property -> responseDTO.addNewItem(
                                        (new InitialEnvironmentItem(property.getPropertyName(),
                                                property.getPropertyValue().toString())))));

        return responseDTO;
    }
}
