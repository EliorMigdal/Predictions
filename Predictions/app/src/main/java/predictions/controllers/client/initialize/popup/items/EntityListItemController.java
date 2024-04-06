package predictions.controllers.client.initialize.popup.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EntityListItemController implements Initializable {
    @FXML private Label entityNameLabel;
    @FXML private Label populationLabel;

    private final EntityListItem entityListItem;

    public EntityListItemController(EntityListItem entityListItem) {
        this.entityListItem = entityListItem;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
    }

    private void bindAll() {
        entityNameLabel.textProperty().bind(entityListItem.entityNameProperty());
        populationLabel.textProperty().bind(entityListItem.populationProperty());
    }
}
