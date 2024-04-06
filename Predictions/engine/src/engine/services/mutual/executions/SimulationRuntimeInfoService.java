package engine.services.mutual.executions;

import dto.request.mutual.executions.SimulationRuntimeInfoRequestDTO;
import dto.response.mutual.executions.SimulationRuntimeInfoResponseDTO;
import engine.Engine;
import engine.services.Service;

public class SimulationRuntimeInfoService implements Service {
    public SimulationRuntimeInfoResponseDTO getSimulationInfo(SimulationRuntimeInfoRequestDTO requestDTO, Engine engine) {
        SimulationRuntimeInfoResponseDTO responseDTO = new SimulationRuntimeInfoResponseDTO();

        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))))
                .findFirst().ifPresent(simulation -> {
                    responseDTO.setErrorMessage(simulation.getSimulationError());
                    responseDTO.setTicksValue(simulation.getCurrentTick().toString());
                    responseDTO.setTicksPercentage(simulation.getTicksPercentage());
                    responseDTO.setTimeValue(simulation.getTimeProgress().toString());
                    responseDTO.setTimePercentage(simulation.getTimePercentage());
                });

        return responseDTO;
    }
}
