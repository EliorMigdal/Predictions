package predictions.controllers.mutual.simulations.items;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import predictions.controllers.mutual.details.DetailsController;
import predictions.controllers.mutual.simulations.SimulationsListController;

import java.io.IOException;

public class SimulationsCellFactory extends ListCell<Simulation> {
    private final DetailsController detailsController;
    private final SimulationsListController listController;

    public SimulationsCellFactory(DetailsController detailsController, SimulationsListController listController) {
        this.detailsController = detailsController;
        this.listController = listController;
    }

    @Override
    protected void updateItem(Simulation item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Mutual/cells/SimulationsCell.fxml"));
            SimulationCellController controller = new SimulationCellController(item, detailsController, listController);
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
