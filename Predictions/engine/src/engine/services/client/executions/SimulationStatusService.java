package engine.services.client.executions;

import dto.request.client.executions.SimulationStatusDTO;
import dto.response.client.executions.SimulationStatusResponseDTO;
import engine.Engine;
import engine.services.Service;

public class SimulationStatusService implements Service {
    public SimulationStatusResponseDTO getSimulationStatus(SimulationStatusDTO requestDTO, Engine engine) {
        SimulationStatusResponseDTO responseDTO = new SimulationStatusResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))))
                .findFirst().ifPresent(simulation -> {
                    responseDTO.setSimulationID(simulation.getSimulationID());
                    responseDTO.setRunning(!simulation.getFinishStatus());
                    responseDTO.setPaused(simulation.getPauseStatus());
                });

        return responseDTO;
    }
}
