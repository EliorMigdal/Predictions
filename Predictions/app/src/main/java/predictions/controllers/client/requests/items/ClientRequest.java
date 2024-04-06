package predictions.controllers.client.requests.items;

import javafx.beans.property.*;
import dto.request.client.requests.TerminationInfoDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClientRequest {
    private final StringProperty requestID;
    private final StringProperty simulationName;
    private final StringProperty total_Remaining;
    private final ObjectProperty<LocalDate> date;
    private final ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList;
    private final StringProperty reqStatus;

    public ClientRequest(String requestID, String simulationName, String amount, LocalDate date, ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList, String reqStatus){
        this.requestID = new SimpleStringProperty(this,"Request",requestID);
        this.simulationName = new SimpleStringProperty(this,"Simulation",simulationName);
        this.total_Remaining = new SimpleStringProperty(this,"Amount",amount);
        this.date = new SimpleObjectProperty<>(this,"Date",date);
        this.terminationInfoDTOArrayList = terminationInfoDTOArrayList;
        this.reqStatus = new SimpleStringProperty(this,"reqStatus",reqStatus);

    }

    public String getReqStatus() {
        return reqStatus.get();
    }

    public StringProperty reqStatusProperty() {
        return reqStatus;
    }

    public StringProperty requestIDProperty() { return  this.requestID; }

    public StringProperty simulationNameProperty() { return  this.simulationName; }

    public StringProperty total_RemainingProperty() { return this.total_Remaining; }

    public ObjectProperty<LocalDate> dataDateProperty() { return this.date; }

    public ArrayList<TerminationInfoDTO> getTerminationInfoDTOArrayList() {
        return terminationInfoDTOArrayList;
    }
}
