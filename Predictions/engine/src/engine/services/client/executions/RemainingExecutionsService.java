package engine.services.client.executions;

import dto.request.client.executions.RemainingExecutionsDTO;
import dto.response.client.executions.RemainingExecutionsResponseDTO;
import engine.Engine;
import engine.services.Service;

public class RemainingExecutionsService implements Service {
    public RemainingExecutionsResponseDTO getNumOfRemainingExecutions(RemainingExecutionsDTO requestDTO, Engine engine) {
        RemainingExecutionsResponseDTO responseDTO = new RemainingExecutionsResponseDTO();
        engine.getClients().stream()
                .filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().flatMap(client -> client.getClientRequests()
                        .stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .findFirst())
                .ifPresent(request -> responseDTO.setRemainingExecutions(request.getRemainingExecutions()));

        return responseDTO;
    }
}
