package engine.services.admin.allocations;

import dto.request.admin.allocations.ApproveRequestDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import engine.Engine;
import engine.services.Service;

public class ApproveRequestService implements Service {
    public EmptyResponseDTO approveRequest(ApproveRequestDTO requestDTO, Engine engine) {
        engine.getClients().stream().filter(client -> client.getClientName().equals(requestDTO.getClientName()))
                .findFirst().flatMap(client -> client.getClientRequests().stream()
                        .filter(request -> request.getRequestID().equals(requestDTO.getRequestID()))
                        .findFirst()).ifPresent(request -> {
                    request.setApprovalStatus(true);
                    engine.getVersionManager().addNewRequest(request);
                });

        return new EmptyResponseDTO();
    }
}
