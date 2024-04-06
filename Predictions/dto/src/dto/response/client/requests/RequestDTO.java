package dto.response.client.requests;

import dto.DTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class RequestDTO implements DTO {
    private final String requestID;
    private final String simulationName;
    private final String total_Remaining;
    private final LocalDate date;
    private final ArrayList<TerminationInfoDTO> terminationInfoDTO;
    private final String reqStatus;

    public RequestDTO(String requestID, String simulationName, String total_Remaining, LocalDate date,
                      ArrayList<TerminationInfoDTO> terminationInfoDTO, String reqStatus) {
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.total_Remaining = total_Remaining;
        this.date = date;
        this.terminationInfoDTO = terminationInfoDTO;
        this.reqStatus = reqStatus;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getTotal_Remaining() {
        return total_Remaining;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<TerminationInfoDTO> getTerminationInfoDTO() {
        return terminationInfoDTO;
    }

    public String getReqStatus() {
        return reqStatus;
    }
}
