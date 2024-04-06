package engine.services.admin.general;

import dto.request.admin.general.ClientNamesRequestDTO;
import dto.response.admin.general.ClientNamesResponseDTO;
import engine.Engine;
import engine.services.Service;

public class ClientsNamesService implements Service {
    public ClientNamesResponseDTO getClientsNames(ClientNamesRequestDTO requestDTO, Engine engine) {
        ClientNamesResponseDTO responseDTO = new ClientNamesResponseDTO();

        engine.getVersionManager().getClientsVersions().keySet().stream()
                .filter(version -> version > requestDTO.getVersion())
                .forEach(version -> {
                    responseDTO.addNewName(engine.getVersionManager().getClientsVersions().get(version));
                    responseDTO.setVersion(version);
                });

        return responseDTO;
    }
}
