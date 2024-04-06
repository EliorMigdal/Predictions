package predictions.controllers.client.executions.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SimulationItemFactory extends ListCell<SimulationItem> {
    @Override
    protected void updateItem(SimulationItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/results/Views/leftscreen/inProgressSimulationListItem.fxml"));
            SimulationItemController controller = new SimulationItemController(item);
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
