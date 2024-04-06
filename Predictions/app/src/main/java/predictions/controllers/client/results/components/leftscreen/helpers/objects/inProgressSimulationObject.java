package predictions.Controllers.Client.results.components.leftscreen.helpers.objects;

import javafx.scene.layout.HBox;

import predictions.Controllers.Client.results.components.leftscreen.listitem.controller.inProgressSimulationListItemController;
public class inProgressSimulationObject {
    private final HBox inProgressHbox;
    private final inProgressSimulationListItemController inProgressSimulationListItemController;

    public inProgressSimulationObject(HBox inProgressHbox,
                                      inProgressSimulationListItemController inProgressSimulationListItemController) {
        this.inProgressHbox = inProgressHbox;
        this.inProgressSimulationListItemController = inProgressSimulationListItemController;
    }

    public HBox getResultsHBOX() {
        return inProgressHbox;
    }

    public inProgressSimulationListItemController getInProgressSimulationListItemController() {
        return inProgressSimulationListItemController;
    }
}
