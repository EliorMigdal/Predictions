package predictions.controllers.admin.executions.items;

import dto.response.admin.execution.ExecutedSimulationDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExecutedSimulation {
    private final StringProperty clientName;
    private final StringProperty requestID;
    private final StringProperty simulationName;
    private final StringProperty simulationID;

    public ExecutedSimulation(ExecutedSimulationDTO responseDTO) {
        this.clientName = new SimpleStringProperty(this, responseDTO.getClientName(), "Client name");
        this.requestID = new SimpleStringProperty(this, responseDTO.getRequestID().toString(), "Request ID");
        this.simulationName = new SimpleStringProperty(this, responseDTO.getSimulationName(), "Simulation name");
        this.simulationID = new SimpleStringProperty(this, responseDTO.getSimulationID().toString(), "Simulation ID");
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

    public StringProperty simulationIDProperty() {
        return simulationID;
    }
}
