package predictions.controllers.admin.threadmanagment.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CurrentlyRunningCellController implements Initializable {
    @FXML private Label requestIDLabel;
    @FXML private Label simulationNameLabel;

    private final CurrentlyRunning currentlyRunningItem;

    public CurrentlyRunningCellController(CurrentlyRunning currentlyRunningItem) {
        this.currentlyRunningItem = currentlyRunningItem;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestIDLabel.textProperty().bind(currentlyRunningItem.requestIDProperty());
        simulationNameLabel.textProperty().bind(currentlyRunningItem.simulationNameProperty());
    }
}
