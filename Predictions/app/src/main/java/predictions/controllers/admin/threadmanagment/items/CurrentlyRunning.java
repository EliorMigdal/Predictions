package predictions.controllers.admin.threadmanagment.items;

import dto.response.admin.activity.CurrentlyRunningDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurrentlyRunning {
    private final StringProperty simulationName;
    private final StringProperty requestID;

    public CurrentlyRunning(CurrentlyRunningDTO responseDTO) {
        this.simulationName = new SimpleStringProperty(responseDTO.getSimulationName());
        this.requestID = new SimpleStringProperty(responseDTO.getRequestID());
    }

    public StringProperty simulationNameProperty() {
        return simulationName;
    }

    public StringProperty requestIDProperty() {
        return requestID;
    }
}
