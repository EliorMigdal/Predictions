package predictions.views.Factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.client.cells.ExecutedCellController;
import predictions.models.templates.Executed;

import java.io.IOException;

public class ExecutedCellFactory extends ListCell<Executed> {
    @Override
    protected void updateItem(Executed item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/cells/ExecutedCell.fxml"));
            ExecutedCellController controller = new ExecutedCellController(item);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
