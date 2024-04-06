package engine.services.mutual.details.properties;

import dto.request.mutual.details.properties.GridPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.Engine;
import engine.services.Service;

public class GridPropertyService implements Service {
    public PropertyResponseDTO provideService(GridPropertyRequestDTO requestDTO, Engine engine) {
        PropertyResponseDTO responseDTO = new PropertyResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().ifPresent(simulation -> {
                    responseDTO.addProperty(new PropertyDTO("X-Value:",
                            ((Integer) simulation.getPRDGrid().getColumns()).toString()));
                    responseDTO.addProperty(new PropertyDTO("Y-Value:",
                            ((Integer) simulation.getPRDGrid().getRows()).toString()));
                });

        return responseDTO;
    }
}
