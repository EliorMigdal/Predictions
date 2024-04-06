package predictions.controllers.client.initialize.popup.items;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EnvironmentListItem {
    private final StringProperty propertyName;
    private final StringProperty propertyValue;

    public EnvironmentListItem(String propertyName, String propertyValue) {
        this.propertyName = new SimpleStringProperty(propertyName);
        this.propertyValue = new SimpleStringProperty(propertyValue);
    }

    public StringProperty propertyNameProperty() {
        return propertyName;
    }

    public StringProperty propertyValueProperty() {
        return propertyValue;
    }
}
