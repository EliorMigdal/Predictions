package predictions.controllers.client.initialize.popup.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EnvironmentListItemController implements Initializable {
    @FXML private Label propertyNameLabel;
    @FXML private Label valueLabel;

    private final EnvironmentListItem environmentListItem;

    public EnvironmentListItemController(EnvironmentListItem environmentListItem) {
        this.environmentListItem = environmentListItem;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        propertyNameLabel.textProperty().bind(environmentListItem.propertyNameProperty());
        valueLabel.textProperty().bind(environmentListItem.propertyValueProperty());
    }
}
