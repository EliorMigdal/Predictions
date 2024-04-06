package predictions.controllers.mutual.simulations.items;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Simulation {
    private final StringProperty simulationName;
    private final ObjectProperty<LocalDate> date;
    private SimulationCellController myController = null;

    public Simulation(String simulationName, LocalDate date) {
        this.simulationName = new SimpleStringProperty(this, "Simulation Name", simulationName);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
    }

    public StringProperty simulationNameProperty() { return this.simulationName; }

    public ObjectProperty<LocalDate> dataProperty() { return this.date; }

    public void setMyController(SimulationCellController myController) {
        this.myController = myController;
    }

    public void removeCircleFill() {
        if (myController != null) {
            myController.unFillCircle();
        }
    }

    public Boolean isCircleFilled() {
        if (myController != null) {
            return myController.isCircleFilled();
        } else {
            return null;
        }
    }

    public String getSimulationName() {
        return simulationName.get();
    }
}
