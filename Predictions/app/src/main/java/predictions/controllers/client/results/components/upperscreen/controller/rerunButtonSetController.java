package predictions.Controllers.Client.results.components.upperscreen.controller;

import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.request.exception.SetValueError;
import firewall.utility.exception.LoadException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import predictions.Controllers.Client.results.controller.ResultsController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class rerunButtonSetController  implements Initializable {


    @FXML private ResultsController parentController;
    @FXML private Button rerunSimulationButton;

    @FXML
    void reRunSimulation(ActionEvent event)
            throws LoadSimulationError, SetValueError, IOException, RequestException, LoadException {
       //parentController.handleReRun();
    }

    public void setParentController(ResultsController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
