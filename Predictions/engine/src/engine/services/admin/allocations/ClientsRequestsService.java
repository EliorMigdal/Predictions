package engine.services.admin.allocations;

import dto.request.admin.allocations.GetRequestsInfoDTO;
import dto.response.admin.allocations.RequestDTO;
import dto.response.admin.allocations.RequestsInfoDTO;
import engine.Engine;
import engine.client.request.Request;
import engine.services.Service;

public class ClientsRequestsService implements Service {
    public RequestsInfoDTO getRequestsInfo(GetRequestsInfoDTO requestDTO, Engine engine) {
        RequestsInfoDTO responseDTO = new RequestsInfoDTO();

        engine.getVersionManager().getRequestsVersions().getRequestsVersion().keySet().stream()
                        .filter(version -> version > requestDTO.getVersion())
                .forEach(match -> {
                    Request requestData = engine.getVersionManager().getRequestsVersions().
                            getRequestsVersion().get(match);
                    RequestDTO newItem = new RequestDTO();
                    newItem.setRequestDate(requestData.getRequestDate().toString());
                    newItem.setRequestID(requestData.getRequestID().toString());
                    newItem.setRequestStatus(requestData.getReqStatus());
                    newItem.setAmount(requestData.getNumOfExecutions().toString());
                    newItem.setClientName(requestData.getClientName());
                    newItem.setTerminations(requestData.getTerminationInfo());
                    newItem.setSimulationName(requestData.getSimulationName());
                    responseDTO.addNewItem(newItem);
                    responseDTO.setVersion(match);
                });

        return responseDTO;
    }
}
