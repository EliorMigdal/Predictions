package predictions.controllers.client.results.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {


    @FXML
    private VBox inProgressSimulationsLayout;

    @FXML
    private VBox finishedSimulationLayout;

    @FXML
    private ProgressBar ticksProgressBar;

    @FXML
    private Label ticksProgressPercent;

    @FXML
    private TextArea simulationErrorLabel;

    @FXML
    private ProgressBar timeProgressBar;

    @FXML
    private Label timeProgressPercent;

    @FXML
    private HBox inProgressButtonsLayout;

    @FXML
    private Label errorSimulationTitleLabel;

    @FXML
    private VBox entitiesSimulationDetailsLayout;

    @FXML
    private HBox reRunButtonSetLayout;

    @FXML
    private HBox futurePastButtonsLayout;

    @FXML
    private HBox executionResultsLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
