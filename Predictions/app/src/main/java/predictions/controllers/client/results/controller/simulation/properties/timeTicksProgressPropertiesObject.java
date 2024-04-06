package predictions.Controllers.Client.results.controller.simulation.properties;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class timeTicksProgressPropertiesObject {

    private SimpleDoubleProperty timeProgressProp;
    private SimpleDoubleProperty ticksProgressProp;
    private SimpleStringProperty ticksTextLabelProp;
    private SimpleStringProperty timeTextLabelProp;

    public timeTicksProgressPropertiesObject() {
        ticksProgressProp = new SimpleDoubleProperty();
        timeProgressProp = new SimpleDoubleProperty();
        ticksTextLabelProp = new SimpleStringProperty("");
        timeTextLabelProp = new SimpleStringProperty("");
    }

    public SimpleDoubleProperty timeProgressPropProperty() {
        return timeProgressProp;
    }

    public SimpleDoubleProperty ticksProgressPropProperty() {
        return ticksProgressProp;
    }

    public String getTicksTextLabelProp() {
        return ticksTextLabelProp.get();
    }

    public SimpleStringProperty ticksTextLabelPropProperty() {
        return ticksTextLabelProp;
    }

    public SimpleStringProperty timeTextLabelPropProperty() {
        return timeTextLabelProp;
    }
}
