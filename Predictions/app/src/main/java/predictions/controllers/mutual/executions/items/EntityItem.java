package predictions.controllers.mutual.executions.items;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BorderPane;

public class EntityItem {
    private final StringProperty entityName;
    private final StringProperty entityCount;
    private final BorderPane entityBox;

    public EntityItem(String entityName, String entityCount, BorderPane entityBox) {
        this.entityName = new SimpleStringProperty(entityName);
        this.entityCount = new SimpleStringProperty(entityCount);
        this.entityBox = entityBox;
    }

    public StringProperty entityNameProperty() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName.set(entityName);
    }

    public StringProperty entityCountProperty() {
        return entityCount;
    }

    public void setEntityCount(String entityCount) {
        this.entityCount.set(entityCount);
    }

    public BorderPane getEntityBox() {
        return entityBox;
    }
}
