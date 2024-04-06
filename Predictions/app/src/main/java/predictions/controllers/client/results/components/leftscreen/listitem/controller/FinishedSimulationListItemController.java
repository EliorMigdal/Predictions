package predictions.Controllers.Client.results.components.leftscreen.listitem.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import predictions.Controllers.Client.results.components.leftscreen.helpers.simulationItem;
import predictions.Controllers.Client.results.components.leftscreen.helpers.type.finishedSimulationItem;
import predictions.Controllers.Client.results.controller.ResultsController;
public class FinishedSimulationListItemController implements Initializable {

    @FXML
    private ResultsController parentController;
    @FXML
    private HBox individualExecuteLabel;

    @FXML
    private ImageView statusImage;
    @FXML
    private Label executionName;

    private  final simulationItem finishedExecution;

    private SimpleStringProperty RunTimeErrorMessage;

    private Integer simulationID;



    public void setParentController(ResultsController parentController) {
        this.parentController = parentController;
    }

    public FinishedSimulationListItemController() {
        this.finishedExecution = new finishedSimulationItem();

    }

    @FXML
    void ExecuteNewSimulation(MouseEvent event) throws IOException {
//        parentController.setCurrentSimulationID(this.simulationID);
//        parentController.setLowerScreen(this.simulationID);
//        parentController.setFinishedSimulationEntityCountBind(this.simulationID);
//        parentController.cleanInProgressButtonSet();
//        parentController.showRerunButtonSet();
//        parentController.clearFuturePastButtonSetForStop();
    }
    public void setStatusImage(Image statusImage,String userData) {
        this.statusImage.setImage(statusImage);
        this.statusImage.setUserData(userData);
    }

    public void setInProgressItem(String finishedSimID) {
        this.finishedExecution.setName(finishedSimID);
        this.simulationID = Integer.parseInt(Arrays.stream(finishedSimID.split(": "))
                .collect(Collectors.toList()).get(1));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RunTimeErrorMessage =new SimpleStringProperty("");
//
//        statusImage.setImage(new Image(getClass().getResourceAsStream("/Client/results/components/leftscreen/listitem/controller/images/TaskCompleteIcon.png")));
//        statusImage.setFitHeight(30);
//        statusImage.setFitWidth(30);
//        statusImage.setUserData("TaskCompleteIcon.png");
        this.executionName.textProperty().bind(finishedExecution.getStringProp());
    }
}
