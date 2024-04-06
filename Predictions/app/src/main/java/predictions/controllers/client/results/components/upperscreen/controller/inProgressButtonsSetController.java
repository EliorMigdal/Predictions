package predictions.Controllers.Client.results.components.upperscreen.controller;

import firewall.request.UIRequest;
import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.utility.exception.LoadException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import predictions.Controllers.Client.results.controller.ResultsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class inProgressButtonsSetController implements Initializable {
    private ResultsController parentController;
    @FXML private Button resumeButton;
    @FXML private Button pauseButton;
    @FXML private Button stopButton;



    private final SimpleBooleanProperty isStopAvailable = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty isResumeAvailable = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isPauseAvailable = new SimpleBooleanProperty(true);

    public inProgressButtonsSetController() {
    }

    public void setParentController(ResultsController parentController) {
        this.parentController = parentController;
    }
    @FXML
    void pauseSimulation(ActionEvent event) throws LoadSimulationError, RequestException, LoadException, IOException {
//        handleSimulationAction(new PauseSimulationRequest(parentController.getCurrentSimulationID()),
//                true, false, true, "/layouts/results/components/leftscreen/listitem/controller/images/PauseIcon.png"
//        ,"PauseIcon.png");
//        parentController.showFuturePastButtonSet();
//        parentController.setLowerScreen(parentController.getCurrentSimulationID());

    }

    @FXML
    void stopSimulation(ActionEvent event) throws LoadSimulationError, RequestException, LoadException {
//        handleSimulationAction(new StopSimulationRequest(parentController.getCurrentSimulationID()),
//                false, false, false, "/layouts/results/components/leftscreen/listitem/controller/images/StopIcon.png"
//        ,"StopIcon.png");
//        parentController.clearFuturePastButtonSetForStop();

    }

    @FXML
    void resumeSimulation(ActionEvent event) throws LoadSimulationError, RequestException, LoadException {
//        handleSimulationAction(new ResumeSimulationRequest(parentController.getCurrentSimulationID()),
//                false, true, true, "/layouts/results/components/leftscreen/listitem/controller/images/ResumeIcon.png"
//                ,"ResumeIcon.png");
//        parentController.clearFuturePastButtonSetForResume();
    }

    private void handleSimulationAction(UIRequest request, boolean resume, boolean pause, boolean stop, String statusImageResource,String imageUserData) throws LoadSimulationError, RequestException, LoadException {
//        parentController.getFireWall().handleRequest(request);
//        isPauseAvailable.set(pause);
//        isResumeAvailable.set(resume);
//        isStopAvailable.set(stop);
//
//        if (statusImageResource != null) {
//            inProgressSimulationListItemController inProgressController = parentController.getCurrInProgressController();
//            inProgressController.setStatusImage(new Image(getClass().getResourceAsStream(statusImageResource)),imageUserData);
//        }
    }

    public void togglePausedSimulation() {
        isPauseAvailable.set(true);
        isStopAvailable.set(true);
        isResumeAvailable.set(false);
    }

    public void toggleResumedSimulation() {
        isPauseAvailable.set(false);
        isStopAvailable.set(true);
        isResumeAvailable.set(true);
    }

    public void toggleFinishSimulationButtons(boolean isActive) {
        isStopAvailable.set(isActive);
        isResumeAvailable.set(!isActive);
        isPauseAvailable.set(isActive);
    }

    public void toggleQueuedSimulationButtons() {
        isPauseAvailable.set(false);
        isResumeAvailable.set(false);
        isStopAvailable.set(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resumeButton.disableProperty().bind(isResumeAvailable.not());
        stopButton.disableProperty().bind(isStopAvailable.not());
        pauseButton.disableProperty().bind(isPauseAvailable.not());
    }
}
