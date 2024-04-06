package engine.service.client.user.type;

import engine.EngineService;
import engine.client.request.Request;
import engine.service.client.user.UserRequest;
import engine.service.exception.InitException;
import engine.simulation.Simulation;
import dto.request.client.InitialEntitiesDTO;
import dto.request.client.InitialEnvironmentDTO;
import dto.request.client.SimulationExecutionDetailsDTO;

import java.util.ArrayList;
import java.util.Optional;

public class GetSpecificRequestExecutionDetails implements UserRequest {
    private EngineService engine;

    public GetSpecificRequestExecutionDetails() {
    }

    public GetSpecificRequestExecutionDetails(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws InitException {

        String clientName = (String) args[0];
        String requestID = (String) args[1];

        Optional<Request> clientRequestData = engine.getRequestData(clientName, Integer.valueOf(requestID));

        ArrayList<InitialEntitiesDTO> initialEntitiesDTOS = new ArrayList<>();
        ArrayList<InitialEnvironmentDTO> initialEnvironmentDTOS = new ArrayList<>();
        clientRequestData.ifPresent(data ->{
            Simulation simulationObject = data.getSimulationSample();
            simulationObject.getWorld().getEntities()
                    .forEach(entity->
                            initialEntitiesDTOS.add(new InitialEntitiesDTO(entity.getEntityName())));
            simulationObject.getWorld()
                    .getEnvironmentVariables()
                    .getProperties()
                    .forEach(property -> {
                        initialEnvironmentDTOS.add(new InitialEnvironmentDTO(
                                property.getPropertyName(),
                                String.valueOf(property.getPropertyValue()),
                                "From " + property.getPropertyRange().getMinValue().toString()
                                + " to " + property.getPropertyRange().getMaxValue().toString()
                        ));
                    });
        });
        return new SimulationExecutionDetailsDTO(initialEntitiesDTOS,initialEnvironmentDTOS);
    }
}
