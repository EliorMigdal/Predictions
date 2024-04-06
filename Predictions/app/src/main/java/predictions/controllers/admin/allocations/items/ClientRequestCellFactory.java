package predictions.controllers.admin.allocations.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.admin.allocations.AllocationsController;

import java.io.IOException;

public class ClientRequestCellFactory extends ListCell<ClientRequest> {
    private final AllocationsController parentController;

    public ClientRequestCellFactory(AllocationsController parentController) {
        this.parentController = parentController;
    }

    @Override
    protected void updateItem(ClientRequest item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/cells/AdminRequestsCell.fxml"));
            ClientRequestController controller = new ClientRequestController(item, parentController);
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
