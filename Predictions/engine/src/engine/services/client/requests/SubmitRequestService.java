package engine.services.client.requests;

import dto.request.client.requests.SubmitRequestDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.Engine;
import engine.client.request.Request;
import engine.services.Service;

public class SubmitRequestService implements Service {
    public EmptyResponseDTO submitRequest(SubmitRequestDTO requestDTO, Engine engine) {
        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().ifPresent(client -> {
                    Request newRequest = new Request(requestDTO.getClientName(),
                            client.getClientRequests().size() + 1, requestDTO.getAmount().toString(),
                            requestDTO.getSimulationName(), requestDTO.getTerminationInfo());
                    client.addNewRequest(newRequest);
                    engine.getVersionManager().addNewRequest(newRequest);
                });

        return new EmptyResponseDTO();
    }
}
