package dto.request.client;

import dto.DTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.util.ArrayList;

public class ClientRequestNoDateDTO implements DTO {
    private final String clientName;
    private final String simulationName;
    private final String amount;
    private final ArrayList<TerminationInfoDTO> terminationInfoDTO;

    public ClientRequestNoDateDTO(String clientName, String simulationName, String amount, ArrayList<TerminationInfoDTO> terminationInfoDTO){
        this.clientName = clientName;
        this.simulationName = simulationName;
        this.amount = amount;
        this.terminationInfoDTO = terminationInfoDTO;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getAmount() {
        return amount;
    }

    public ArrayList<TerminationInfoDTO> getTerminationInfoDTO() {
        return terminationInfoDTO;
    }
}
