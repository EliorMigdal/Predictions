package predictions.controllers.client.initialize.popup.items.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.client.initialize.popup.items.EnvironmentListItem;
import predictions.controllers.client.initialize.popup.items.EnvironmentListItemController;

import java.io.IOException;

public class EnvironmentItemFactory extends ListCell<EnvironmentListItem> {
    @Override
    protected void updateItem(EnvironmentListItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views" +
                    "/execution/Views/EnvironmentListItem.fxml"));
            try {
                EnvironmentListItemController controller = new EnvironmentListItemController(item);
                loader.setController(controller);
                setText(null);
                setGraphic(loader.load());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
