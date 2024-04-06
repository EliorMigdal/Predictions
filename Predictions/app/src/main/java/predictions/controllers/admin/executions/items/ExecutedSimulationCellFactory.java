package predictions.controllers.admin.executions.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.admin.executions.items.controllers.AdminExecutedCellController;

import java.io.IOException;

public class ExecutedSimulationCellFactory extends ListCell<ExecutedSimulation> {
    @Override
    protected void updateItem(ExecutedSimulation item, boolean empty) {
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/execution_history" +
                    "/Views/AdminFinishedSimulationTile.fxml"));
            AdminExecutedCellController controller = new AdminExecutedCellController(item);
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
