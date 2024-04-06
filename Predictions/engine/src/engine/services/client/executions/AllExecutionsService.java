package engine.services.client.executions;

import dto.request.client.executions.AllExecutionsDTO;
import dto.response.client.executions.AllExecutionsResponseDTO;
import dto.response.client.executions.ExecutionDTO;
import engine.Engine;
import engine.services.Service;

public class AllExecutionsService implements Service {
    public AllExecutionsResponseDTO getAllExecutions(AllExecutionsDTO requestDTO, Engine engine) {
        AllExecutionsResponseDTO responseDTO = new AllExecutionsResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().ifPresent(client -> client.getClientRequests()
                        .forEach(request -> request.getSimulations()
                                .forEach(simulation -> responseDTO.addNewExecutionItem(new ExecutionDTO(
                                        request.getRequestID(), simulation.getSimulationName(),
                                        simulation.getSimulationID(),
                                        !simulation.getFinishStatus() && simulation.hasStartedRunning()
                                )))));

        return responseDTO;
    }
}
