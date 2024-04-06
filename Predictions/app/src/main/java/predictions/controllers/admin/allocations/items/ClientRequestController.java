package predictions.controllers.admin.allocations.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import predictions.controllers.admin.allocations.AllocationsController;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRequestController implements Initializable {
    @FXML private Label amountLabel;
    @FXML private Label clientNameLabel;
    @FXML private Label dateLabel;
    @FXML private Label requestIDLabel;
    @FXML private Circle selectionCircle;
    @FXML private Label simulationNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label viewTerminations;

    private final ClientRequest clientRequest;
    private final AllocationsController parentController;

    public ClientRequestController(ClientRequest clientRequest, AllocationsController parentController) {
        this.clientRequest = clientRequest;
        this.parentController = parentController;
        clientRequest.setMyController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        handleStatusUpdate();
        initializeSelectionCircle();
        initializePlusLabel();
    }

    public void handleStatusUpdate() {
        if (statusLabel.textProperty().get().equals("Approved")) {
            statusLabel.setStyle("-fx-text-fill: #ffffff;");
            statusLabel.setStyle("-fx-background-color: rgb(0,255,7);");
        } else if (statusLabel.textProperty().get().equals("Declined")) {
            statusLabel.setStyle("-fx-text-fill: #ffffff;");
            statusLabel.setStyle("-fx-background-color: rgb(255,82,82);");
        } else if (statusLabel.textProperty().get().equals("Waiting")) {
            statusLabel.setStyle("-fx-text-fill: #000000");
            statusLabel.setStyle("-fx-background-color: #ffff00;");
        }
    }

    public void bindAll() {
        this.amountLabel.textProperty().bind(clientRequest.amountProperty());
        this.clientNameLabel.textProperty().bind(clientRequest.clientNameProperty());
        this.dateLabel.textProperty().bind(clientRequest.dateProperty());
        this.requestIDLabel.textProperty().bind(clientRequest.requestIDProperty());
        this.simulationNameLabel.textProperty().bind(clientRequest.simulationNameProperty());
        this.statusLabel.textProperty().bind(clientRequest.requestStatusProperty());
    }

    public void initializeSelectionCircle() {
        selectionCircle.setOnMousePressed(event -> handleCirclePress());
    }

    public void handleCirclePress() {
        if (this.selectionCircle.getFill() != Color.BLACK) {
            this.parentController.unFillAllRequests();
            this.selectionCircle.setFill(Color.BLACK);
            this.parentController.setSelectedClient(clientNameLabel.textProperty().get());
            this.parentController.setSelectedRequestID(requestIDLabel.textProperty().get());
            this.parentController.setCurrentlySelectedItemController(this);
            if (statusLabel.textProperty().get().equals("Waiting")) {
                parentController.enableButtons();
            } else {
                parentController.disableButtons();
            }
        } else {
            this.selectionCircle.setFill(Color.WHITE);
            this.parentController.setSelectedClient("");
            this.parentController.setSelectedRequestID("");
            if (statusLabel.textProperty().get().equals("Waiting")) {
                parentController.disableButtons();
            }
        }
    }

    public void unFillCircle() {
        this.selectionCircle.setFill(Color.WHITE);
    }

    public void initializePlusLabel() {
        StringBuilder terminationsString = new StringBuilder();

        if (clientRequest.getTerminationsInfo().size() > 1) {
            terminationsString.append("Terminations:\n");
        } else {
            terminationsString.append("Termination:\n");
        }

        clientRequest.getTerminationsInfo().forEach(termination -> {
            if (termination.getTerminationTerm().equals("User")) {
                terminationsString.append(termination.getTermName()).append("\n");
            } else {
                terminationsString.append(termination.getTermName()).append(": ")
                        .append(termination.getTerminationTerm()).append("\n");
            }
        });
        Tooltip tooltip = new Tooltip(terminationsString.toString());
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(viewTerminations, tooltip);
    }

    public void disableSelectionButton() {
        this.selectionCircle.setDisable(true);
    }
}
