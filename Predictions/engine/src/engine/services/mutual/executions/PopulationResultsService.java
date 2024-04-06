package engine.services.mutual.executions;

import dto.request.mutual.executions.PopulationResultsRequestDTO;
import dto.response.mutual.executions.EntityPopulationDTO;
import dto.response.mutual.executions.PopulationDTO;
import dto.response.mutual.executions.PopulationResultsResponseDTO;
import engine.Engine;
import engine.services.Service;

public class PopulationResultsService implements Service {
    public PopulationResultsResponseDTO getPopulationHistory(PopulationResultsRequestDTO requestDTO, Engine engine) {
        PopulationResultsResponseDTO responseDTO = new PopulationResultsResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))
                                .flatMap(simulation -> simulation.getWorld().getEntities().stream())))
                                        .forEach(entity -> {
                                            EntityPopulationDTO newItem = new EntityPopulationDTO();
                                            newItem.setEntityName(entity.getEntityName());
                                            entity.getPopulationHistory().keySet().forEach(tick ->
                                                    newItem.addPopulationItem
                                                    (new PopulationDTO(tick, entity.getPopulationHistory().get(tick))));
                                            responseDTO.addNewPopulationItem(newItem);
                                        });

        return responseDTO;
    }
}
