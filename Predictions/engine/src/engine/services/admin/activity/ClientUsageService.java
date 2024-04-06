package engine.services.admin.activity;

import dto.request.admin.activity.ClientUsageRequestDTO;
import dto.response.admin.activity.ClientUsageDTO;
import dto.response.admin.activity.ClientUsageResponseDTO;
import engine.Engine;
import engine.services.Service;
import engine.version.types.usage.ClientUsageUpdate;

public class ClientUsageService implements Service {
    public ClientUsageResponseDTO provideService(ClientUsageRequestDTO requestDTO, Engine engine) {
        ClientUsageResponseDTO responseDTO = new ClientUsageResponseDTO();

        engine.getVersionManager().getUsageVersions().getUsageUpdates().keySet()
                .stream().filter(version -> version > requestDTO.getVersion())
                .forEach(match -> {
                    ClientUsageUpdate usageUpdate = engine.getVersionManager().getUsageVersions()
                            .getUsageUpdates().get(match);
                    responseDTO.addNewUsageUpdate(new ClientUsageDTO(usageUpdate.getClientName(),
                            usageUpdate.getNumOfRunning(), usageUpdate.getNumOfWaiting(),
                            usageUpdate.getNumOfFinished()));
                    responseDTO.setVersion(match);
                });

        return responseDTO;
    }
}
