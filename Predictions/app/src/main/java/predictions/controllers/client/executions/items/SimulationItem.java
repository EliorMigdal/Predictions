package predictions.controllers.client.executions.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import predictions.controllers.client.initialize.items.ExecutionInfo;

public class SimulationItem {
    private final StringProperty simulationID;
    private final BooleanProperty isRunning = new SimpleBooleanProperty(true);

    private ExecutionInfo executionInfo;
    private SimulationItemController myController;

    public SimulationItem(Integer simulationID) {
        this.simulationID = new SimpleStringProperty("Simu#" + simulationID.toString());
    }

    public StringProperty simulationIDProperty() {
        return simulationID;
    }

    public void setRunning(boolean running) {
        isRunning.set(running);
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void setExecutionInfo(ExecutionInfo executionInfo) {
        this.executionInfo = executionInfo;
    }

    public ExecutionInfo getExecutionInfo() {
        return executionInfo;
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public void setMyController(SimulationItemController myController) {
        this.myController = myController;
    }

    public void updateMenuItems() {
        this.myController.updateMenuItems();
    }

    public void updateLineChart() {
        this.myController.updatePopulationLineChart();
    }
}
