package predictions.controllers.admin.allocations.items;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import dto.response.admin.allocations.RequestDTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.util.ArrayList;

public class ClientRequest {
    private final StringProperty clientName;
    private final StringProperty requestID;
    private final StringProperty simulationName;
    private final StringProperty amount;
    private final StringProperty date;
    private final StringProperty requestStatus;
    private final ArrayList<TerminationInfoDTO> terminationsInfo;

    private ClientRequestController myController;

    public ClientRequest(RequestDTO adminRequest) {
        this.clientName = new SimpleStringProperty(this, "Client", adminRequest.getClientName());
        this.requestID = new SimpleStringProperty(this, "Request", adminRequest.getRequestID());
        this.simulationName = new SimpleStringProperty(this, "Simulation", adminRequest.getSimulationName());
        this.amount = new SimpleStringProperty(this, "Amount", adminRequest.getAmount());
        this.date = new SimpleStringProperty(this, "Date", adminRequest.getRequestDate());
        this.requestStatus = new SimpleStringProperty(this, "Status", adminRequest.getRequestStatus());
        this.terminationsInfo = adminRequest.getTerminations();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public StringProperty requestIDProperty() {
        return requestID;
    }

    public StringProperty simulationNameProperty() {
        return simulationName;
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty requestStatusProperty() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus.set(requestStatus);
        this.myController.handleStatusUpdate();
    }

    public void setMyController(ClientRequestController myController) {
        this.myController = myController;
    }

    public void unFillCircle() {
        this.myController.unFillCircle();
    }

    public ArrayList<TerminationInfoDTO> getTerminationsInfo() {
        return terminationsInfo;
    }
}
