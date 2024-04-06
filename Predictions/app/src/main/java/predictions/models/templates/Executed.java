package predictions.models.templates;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Executed {
    private final StringProperty requestID;
    private final StringProperty simulationName;
    private final StringProperty simulationID;
    private final ObjectProperty<LocalDate> date;

    public Executed(String requestID, String simulationName,String simulationID, LocalDate date){
        this.requestID = new SimpleStringProperty(this,"Request",requestID);
        this.simulationName = new SimpleStringProperty(this,"Simulation Name",simulationName);
        this.simulationID = new SimpleStringProperty(this,"Simulation ID",simulationID);
        this.date = new SimpleObjectProperty<>(this,"Date",date);
    }

    public StringProperty requestIDProperty() { return  this.requestID; }

    public StringProperty simulationIDProperty() { return  this.simulationID; }

    public StringProperty simulationNameProperty() { return  this.simulationName; }

    public ObjectProperty<LocalDate> dataProperty() { return this.date; }
}
