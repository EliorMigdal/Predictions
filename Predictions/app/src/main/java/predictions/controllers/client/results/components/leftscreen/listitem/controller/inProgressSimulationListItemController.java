package predictions.Controllers.Client.results.components.leftscreen.listitem.controller;

import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.utility.exception.LoadException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import predictions.Controllers.Client.results.components.leftscreen.helpers.simulationItem;
import predictions.Controllers.Client.results.controller.ResultsController;
import predictions.Controllers.Client.results.components.leftscreen.helpers.type.inProgressSimulationItem;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class inProgressSimulationListItemController implements Initializable {


    //resultController....
    @FXML
    private ResultsController parentController;
    @FXML
    private HBox individualExecuteLabel;

    @FXML
    private Label executionName;

    @FXML
    private ImageView statusImage;

    private Integer simulationID;

    private SimpleStringProperty RunTimeErrorMessage;

    private  final simulationItem inProgressSimulationID;

    public void setParentController(ResultsController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void ExecuteNewSimulation(MouseEvent event)
            throws LoadSimulationError, RequestException, LoadException, IOException {

//        parentController.setCurrentSimulationID(simulationID);
//        parentController.cleanLowerResultScreen();
//        parentController.cleanRerunButtonSetAndErrorMessage();
//        parentController.clearFuturePastButtonSetForStop();
//        parentController.showInProgressButtonSet(simulationID);
//        if(statusImage.getUserData().equals("PauseIcon.png")){
//            parentController.showFuturePastButtonSet();
//            parentController.setLowerScreen(simulationID);
//        }
    }

    public ImageView getStatusImage() {
        return statusImage;
    }

    public String getRunTimeErrorMessage() {
        return RunTimeErrorMessage.get();
    }

    public SimpleStringProperty runTimeErrorMessageProperty() {
        return RunTimeErrorMessage;
    }

    public void setRunTimeErrorMessage(String runTimeErrorMessage) {
        this.RunTimeErrorMessage.set(runTimeErrorMessage);
    }

    public void setStatusImage(Image statusImage, String userData) {
        this.statusImage.setImage(statusImage);
        this.statusImage.setUserData(userData);

    }

    public simulationItem getInProgressSimulation() {
        return inProgressSimulationID;
    }

    public void setInProgressItem(String inProgressSimulationID) {
        this.inProgressSimulationID.setName(inProgressSimulationID);
        this.simulationID = Integer.parseInt(Arrays.stream(inProgressSimulationID.split(": ")).collect(Collectors.toList()).get(1));
    }

    public inProgressSimulationListItemController() {
        this.inProgressSimulationID = new inProgressSimulationItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RunTimeErrorMessage =new SimpleStringProperty("");
//        statusImage.setImage(new Image(getClass().getResourceAsStream("/Client/results/components/leftscreen/listitem/controller/images/ResumeIcon.png")));
//        statusImage.setFitHeight(30);
//        statusImage.setFitWidth(30);
//        statusImage.setUserData("ResumeIcon.png");
        executionName.textProperty().bind(inProgressSimulationID.getStringProp());
    }
}
