package engine.services.admin.general;

import dto.request.admin.general.RequestsSizeRequestDTO;
import dto.response.admin.general.RequestsSizeResponseDTO;
import engine.Engine;
import engine.services.Service;

public class RequestsSizeService implements Service {
    public RequestsSizeResponseDTO getNumOfRequests(RequestsSizeRequestDTO requestDTO, Engine engine) {
        return new RequestsSizeResponseDTO(engine.getClients().stream()
                .filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .mapToInt(client -> client.getClientRequests().size())
                .sum());
    }
}
