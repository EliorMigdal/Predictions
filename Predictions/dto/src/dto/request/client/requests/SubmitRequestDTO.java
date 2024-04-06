package dto.request.client.requests;

import dto.DTO;

import java.util.ArrayList;

public class SubmitRequestDTO implements DTO {
    private final String clientName;
    private final String simulationName;
    private final Integer amount;
    private final ArrayList<TerminationInfoDTO> terminationInfo;

    public SubmitRequestDTO(String clientName, String simulationName, Integer amount,
                            ArrayList<TerminationInfoDTO> terminationInfo) {
        this.clientName = clientName;
        this.simulationName = simulationName;
        this.amount = amount;
        this.terminationInfo = terminationInfo;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public Integer getAmount() {
        return amount;
    }

    public ArrayList<TerminationInfoDTO> getTerminationInfo() {
        return terminationInfo;
    }
}
