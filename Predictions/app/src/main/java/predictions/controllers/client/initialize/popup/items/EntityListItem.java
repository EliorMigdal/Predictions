package predictions.controllers.client.initialize.popup.items;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EntityListItem {
    private final StringProperty entityName;
    private final StringProperty population;

    public EntityListItem(String entityName, String population) {
        this.entityName = new SimpleStringProperty(entityName);
        this.population = new SimpleStringProperty(population);
    }

    public StringProperty entityNameProperty() {
        return entityName;
    }

    public StringProperty populationProperty() {
        return population;
    }
}
