package dto.request.client;

import dto.DTO;
import dto.request.client.InitialEntitiesDTO;
import dto.request.client.InitialEnvironmentDTO;

import java.util.ArrayList;

public class SimulationExecutionDetailsDTO implements DTO {
    private final ArrayList<InitialEntitiesDTO> initialEntitiesDTOS;
    private final ArrayList<InitialEnvironmentDTO> initialEnvironmentDTOS;

    public SimulationExecutionDetailsDTO(ArrayList<InitialEntitiesDTO> initialEntitiesDTOS, ArrayList<InitialEnvironmentDTO> initialEnvironmentDTOS) {
        this.initialEntitiesDTOS = initialEntitiesDTOS;
        this.initialEnvironmentDTOS = initialEnvironmentDTOS;
    }


    public ArrayList<InitialEntitiesDTO> getInitialEntitiesDTOS() {
        return initialEntitiesDTOS;
    }

    public ArrayList<InitialEnvironmentDTO> getInitialEnvironmentDTOS() {
        return initialEnvironmentDTOS;
    }

}
