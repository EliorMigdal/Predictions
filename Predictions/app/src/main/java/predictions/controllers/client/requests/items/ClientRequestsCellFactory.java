package predictions.controllers.client.requests.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ClientRequestsCellFactory extends ListCell<ClientRequest> {
    @Override
    protected void updateItem(ClientRequest item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/cells/ClientRequestsCell.fxml"));
            try {
               ClientRequestsCellController controller = new ClientRequestsCellController(item);
               loader.setController(controller);
               setText(null);
               setGraphic(loader.load());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
