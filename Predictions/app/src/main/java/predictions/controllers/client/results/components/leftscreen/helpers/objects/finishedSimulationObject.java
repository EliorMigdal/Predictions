package predictions.Controllers.Client.results.components.leftscreen.helpers.objects;

import javafx.scene.layout.HBox;
import predictions.Controllers.Client.results.components.leftscreen.listitem.controller.FinishedSimulationListItemController;

public class finishedSimulationObject {
    private final HBox finishedHBOX;
    private final FinishedSimulationListItemController finishedSimulationListItemController;

    public finishedSimulationObject(HBox finishedHBOX, FinishedSimulationListItemController finishedSimulationListItemController) {
        this.finishedHBOX = finishedHBOX;
        this.finishedSimulationListItemController = finishedSimulationListItemController;
    }

    public HBox getResultsHBOX() {
        return finishedHBOX;
    }

    public FinishedSimulationListItemController getFinishedSimulationListItemController() {
        return finishedSimulationListItemController;
    }
}
