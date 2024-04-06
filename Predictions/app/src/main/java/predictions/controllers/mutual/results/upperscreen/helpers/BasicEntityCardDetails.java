package predictions.Controllers.Mutual.results.upperscreen.helpers;

import javafx.beans.property.SimpleStringProperty;

public class BasicEntityCardDetails implements CardDetails {
    protected SimpleStringProperty entityNameProp;
    protected SimpleStringProperty entityCountProp;
    protected Integer workingSimulationID;
    public BasicEntityCardDetails(String entityNameProp, String entityCountProp) {
        this.entityNameProp = new SimpleStringProperty(entityNameProp);
        this.entityCountProp = new SimpleStringProperty(entityCountProp);
    }

    @Override
    public void setSimulationID(Integer SimID) {
        this.workingSimulationID = SimID;
    }

    @Override
    public Integer getSimulationID() {
        return this.workingSimulationID;
    }

    @Override
    public String getName() {
        return entityNameProp.get();
    }

    @Override
    public void setName(String name) {
        this.entityNameProp.set(name);
    }

    @Override
    public String getCount() {
        return this.entityCountProp.get();
    }
    @Override
    public SimpleStringProperty getCountProp(){
        return this.entityCountProp;
    }

    @Override
    public void setCount(String count) {
        this.entityCountProp.set(count);
    }

    @Override
    public String toString() {
        return "BasicEntityCardDetails{" +
                "entityName=" + entityNameProp +
                ", entityCount=" + entityCountProp +
                '}';
    }
}
