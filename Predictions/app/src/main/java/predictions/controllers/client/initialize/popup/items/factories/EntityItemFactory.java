package predictions.controllers.client.initialize.popup.items.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.client.initialize.popup.items.EntityListItem;
import predictions.controllers.client.initialize.popup.items.EntityListItemController;

import java.io.IOException;

public class EntityItemFactory extends ListCell<EntityListItem> {
    @Override
    protected void updateItem(EntityListItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views" +
                    "/execution/Views/EntityListItem.fxml"));
            try {
                EntityListItemController controller = new EntityListItemController(item);
                loader.setController(controller);
                setText(null);
                setGraphic(loader.load());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
