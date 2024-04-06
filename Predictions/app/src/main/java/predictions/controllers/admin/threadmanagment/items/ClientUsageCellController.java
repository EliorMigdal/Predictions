package predictions.controllers.admin.threadmanagment.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import predictions.controllers.admin.threadmanagment.ThreadManagementController;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientUsageCellController implements Initializable {
    @FXML private Label clientName;
    @FXML private Label numOfFinished;
    @FXML private Label numOfRunning;
    @FXML private Label numOfWaiting;
    @FXML private Circle selectionCircle;

    private final ClientUsage clientUsage;
    private final ThreadManagementController parentController;

    public ClientUsageCellController(ClientUsage clientUsage) {
        this.clientUsage = clientUsage;
        this.parentController = Model.getInstance().getControllerFactory().getThreadManagementController();
        clientUsage.setMyController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        initializeSelectionCircle();
    }

    private void bindAll() {
        clientName.textProperty().bind(clientUsage.clientNameProperty());
        numOfWaiting.textProperty().bind(clientUsage.waitingThreadsProperty());
        numOfRunning.textProperty().bind(clientUsage.runningThreadsProperty());
        numOfFinished.textProperty().bind(clientUsage.finishedThreadsProperty());
    }

    private void initializeSelectionCircle() {
        selectionCircle.setOnMousePressed(event -> {
            if (isSelected()) {
                parentController.setCurrentlySelectedClient("");
                unFillCircle();
            } else {
                parentController.unFillAllClients();
                fillCircle();
                parentController.setCurrentlySelectedClient(this.clientName.textProperty().get());
            }
        });
    }

    public void unFillCircle() {
        this.selectionCircle.setFill(Color.WHITE);
    }

    public void fillCircle() {
        this.selectionCircle.setFill(Color.BLACK);
    }

    private boolean isSelected() {
        return this.selectionCircle.getFill() == Color.BLACK;
    }
}
