package predictions.Controllers.Client.results.controller.generator;

import predictions.Controllers.Mutual.results.upperscreen.entityPropertyDetailsCardController;
import javafx.scene.layout.BorderPane;

public class entityCountObject {

    private  entityPropertyDetailsCardController entityPropertyDetailsCardController;
    private final BorderPane currTile;
    private final String entName;

    public entityCountObject(entityPropertyDetailsCardController entityPropertyDetailsCardController, BorderPane currTile, String entName) {
        this.entityPropertyDetailsCardController = entityPropertyDetailsCardController;
        this.currTile = currTile;
        this.entName = entName;
    }

    public entityPropertyDetailsCardController getEntityPropertyDetailsCardController() {
        return entityPropertyDetailsCardController;
    }
}
