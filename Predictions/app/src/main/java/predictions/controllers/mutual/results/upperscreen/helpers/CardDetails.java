package predictions.Controllers.Mutual.results.upperscreen.helpers;

import javafx.beans.property.SimpleStringProperty;

public interface CardDetails {
    void setSimulationID(Integer SimID);
    Integer getSimulationID();
    String getName();
    SimpleStringProperty getCountProp();
    void setName(String name);
    String getCount();
    void setCount(String count);
}
