package predictions.models.logic.fxml.requests;

import javafx.scene.Parent;

public final class FXMLResponseObj {
    private final Parent currBox;
    private final Object controller;

    public FXMLResponseObj() {
        this.currBox = null;
        this. controller = null;
    }

    public FXMLResponseObj(Parent currBox, Object controller) {
        this.currBox = currBox;
        this.controller = controller;
    }

    public Parent getCurrBox() {
        return currBox;
    }

    public Object getController() {
        return controller;
    }


}
