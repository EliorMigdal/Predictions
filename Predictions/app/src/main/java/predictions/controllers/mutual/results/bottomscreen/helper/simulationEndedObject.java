package predictions.Controllers.Mutual.results.bottomscreen.helper;

import javafx.scene.layout.HBox;
import predictions.Controllers.Mutual.results.bottomscreen.controller.SimulationEndedResultsController;

public class simulationEndedObject {
    private final HBox resultsHBOX;
    private final SimulationEndedResultsController simulationEndedResultsController;

    public simulationEndedObject(HBox resultsHBOX, SimulationEndedResultsController simulationEndedResultsController) {
        this.resultsHBOX = resultsHBOX;
        this.simulationEndedResultsController = simulationEndedResultsController;
    }

    public HBox getResultsHBOX() {
        return resultsHBOX;
    }

    public SimulationEndedResultsController getSimulationEndedResultsController() {
        return simulationEndedResultsController;
    }
}
