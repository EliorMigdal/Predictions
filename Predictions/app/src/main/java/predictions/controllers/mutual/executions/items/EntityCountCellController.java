package predictions.controllers.mutual.executions.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EntityCountCellController implements Initializable {
    @FXML private Label entityCount;
    @FXML private Label entityName;

    private EntityItem entityItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEntityName(String entityName) {
        this.entityName.textProperty().set(entityName);
    }

    public void setEntityCount(String entityCount) {
        this.entityCount.textProperty().set(entityCount);
    }

    public void setEntityItem(EntityItem entityItem) {
        this.entityItem = entityItem;
        bindAll();
    }

    private void bindAll() {
        entityName.textProperty().bind(entityItem.entityNameProperty());
        entityCount.textProperty().bind(entityItem.entityCountProperty());
    }
}
