package engine.services.admin.activity;

import dto.request.admin.activity.CurrentlyRunningRequestDTO;
import dto.response.admin.activity.CurrentlyRunningDTO;
import dto.response.admin.activity.CurrentlyRunningResponseDTO;
import engine.Engine;
import engine.services.Service;

public class CurrentlyRunningService implements Service {
    public CurrentlyRunningResponseDTO provideService(CurrentlyRunningRequestDTO requestDTO, Engine engine) {
        CurrentlyRunningResponseDTO responseDTO = new CurrentlyRunningResponseDTO();
        engine.getClients().stream()
                .filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .forEach(client -> client.getClientRequests()
                        .forEach(request -> request.getSimulations().stream()
                                .filter(simulation -> simulation.hasStartedRunning() && !simulation.getFinishStatus())
                                .forEach(simulation -> responseDTO.andNewRunningItem(new CurrentlyRunningDTO(
                                        simulation.getSimulationName(), request.getRequestID().toString()
                                )))));

        return responseDTO;
    }
}
