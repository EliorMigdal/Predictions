package engine.services.mutual.executions;

import dto.request.mutual.executions.PropertyResultsRequestDTO;
import dto.response.mutual.executions.PropertyResultResponseDTO;
import engine.Engine;
import engine.services.Service;

public class PropertyResultsService implements Service {
    public PropertyResultResponseDTO getPropertyResult(PropertyResultsRequestDTO requestDTO, Engine engine) {
        PropertyResultResponseDTO responseDTO = new PropertyResultResponseDTO();

        engine.getClients().stream()
                .filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream())
                .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                .flatMap(request -> request.getSimulations().stream())
                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                .flatMap(simulation -> simulation.getWorld().getEntities().stream())
                .filter(entity -> entity.getEntityName().equals(requestDTO.getEntityName()))
                .flatMap(entity -> entity.getEntityInstances().stream())
                .flatMap(instance -> instance.getProperties().stream())
                .filter(property -> property.getPropertyName().equals(requestDTO.getPropertyName()))
                .forEach(property -> {
                    if (property.getPropertyValue() instanceof Float) {
                        responseDTO.addNewData(((Float) property.getPropertyValue()).intValue());
                    } else {
                        responseDTO.addNewData(property.getPropertyValue());
                    }
                    responseDTO.incrementInstancesSum();
                    responseDTO.increaseConsistencySum(property.calculateConsistency());
                    responseDTO.increaseAverageSum(property.calculateAverage());
                });


        return responseDTO;
    }
}
