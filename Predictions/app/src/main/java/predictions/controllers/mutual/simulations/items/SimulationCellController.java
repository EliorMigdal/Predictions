package predictions.controllers.mutual.simulations.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import predictions.controllers.mutual.details.DetailsController;
import predictions.controllers.mutual.simulations.SimulationsListController;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationCellController implements Initializable {
    @FXML private Circle selectionCircle;
    @FXML private Label dateLabel;
    @FXML private Label simulationName;

    private final Simulation simulation;
    private final DetailsController detailsController;
    private final SimulationsListController listController;

    public SimulationCellController(Simulation simulation, DetailsController detailsController,
                                    SimulationsListController listController) {
        this.simulation = simulation;
        this.detailsController = detailsController;
        this.listController = listController;
        this.simulation.setMyController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        initializeSelectionCircle();
    }

    private void bindAll() {
        dateLabel.textProperty().bind(simulation.dataProperty().asString());
        simulationName.textProperty().bind(simulation.simulationNameProperty());
    }

    private void initializeSelectionCircle() {
        this.selectionCircle.setOnMousePressed(event -> {
            if (isCircleFilled()) {
                unFillCircle();
                detailsController.clearDetailsTreeView();
            } else {
                listController.unFillAllCircles();
                fillCircle();
                detailsController.clearDetailsTreeView();
                detailsController.showSimulationDetails(this.simulationName.getText());
            }
        });
    }

    public void unFillCircle() {
        this.selectionCircle.setFill(Color.WHITE);
    }

    private void fillCircle() {
        this.selectionCircle.setFill(Color.BLACK);
    }

    public Boolean isCircleFilled() {
        return this.selectionCircle.getFill() == Color.BLACK;
    }
}
