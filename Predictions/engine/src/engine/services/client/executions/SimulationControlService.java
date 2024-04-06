package engine.services.client.executions;

import dto.request.client.executions.ControlSimulationDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.Engine;
import engine.services.Service;

public class SimulationControlService implements Service {
    public EmptyResponseDTO controlSimulation(ControlSimulationDTO requestDTO, Engine engine) {
        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .flatMap(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.getSimulationID().equals(requestDTO.getSimulationID()))))
                .findFirst().ifPresent(simulation -> {
                    switch (requestDTO.getControlOption().getValue()) {
                        case "Pause":
                            simulation.setPauseStatus(true);
                            break;
                        case "Start":
                            simulation.setPauseStatus(false);
                            break;
                        case "Stop":
                            simulation.setPauseStatus(false);
                            simulation.setStopStatus(true);
                            break;
                    }
                });

        return new EmptyResponseDTO();
    }
}
