package predictions.Controllers.Client.results.components.leftscreen.helpers;

import javafx.beans.property.SimpleStringProperty;

public interface simulationItem {
    String getName();
    Integer getSimulationID();
    SimpleStringProperty getStringProp();
    void setName(String name);
}
