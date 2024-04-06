package dto.response.admin.allocations;

import dto.DTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.util.ArrayList;

public class RequestDTO implements DTO {
    private String clientName;
    private String requestID;
    private String simulationName;
    private String amount;
    private String requestStatus;
    private String requestDate;
    private ArrayList<TerminationInfoDTO> terminations;

    public RequestDTO() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ArrayList<TerminationInfoDTO> getTerminations() {
        return terminations;
    }

    public void setTerminations(ArrayList<TerminationInfoDTO> terminations) {
        this.terminations = terminations;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
