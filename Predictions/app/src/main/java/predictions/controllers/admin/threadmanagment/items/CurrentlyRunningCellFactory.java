package predictions.controllers.admin.threadmanagment.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class CurrentlyRunningCellFactory extends ListCell<CurrentlyRunning> {
    @Override
    protected void updateItem(CurrentlyRunning item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/cells/CurrentlyRunningCell.fxml"));
            CurrentlyRunningCellController controller = new CurrentlyRunningCellController(item);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
