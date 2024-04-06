package predictions.Controllers.Client.results.components.leftscreen.helpers.type;

import javafx.beans.property.SimpleStringProperty;
import predictions.Controllers.Client.results.components.leftscreen.helpers.simulationItem;

import java.util.Arrays;
import java.util.stream.Collectors;

public class finishedSimulationItem implements simulationItem {

    protected SimpleStringProperty SimulationID;

    public finishedSimulationItem() {
        this.SimulationID = new SimpleStringProperty("");
    }



    public finishedSimulationItem(String simulationID) {
        SimulationID = new SimpleStringProperty(simulationID);
    }

    @Override
    public String getName() {
        return SimulationID.get();
    }

    @Override
    public Integer getSimulationID() {
        return Integer.parseInt(Arrays.stream(SimulationID.get().split(": ")).collect(Collectors.toList()).get(1));

    }

    @Override
    public SimpleStringProperty getStringProp() {
        return this.SimulationID;
    }

    @Override
    public void setName(String name) {
        this.SimulationID.set(name);
    }
}
