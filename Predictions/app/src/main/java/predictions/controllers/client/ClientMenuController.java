package predictions.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.type.Interaction.LogoutRequest;
import predictions.views.Enums.ClientMenuOptions;
import predictions.views.Enums.HttpMethods;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    @FXML public HBox HomePartition_btn;
    @FXML public HBox detailsPartition_btn;
    @FXML public HBox RequestsPartition_btn;
    @FXML public HBox ExecutePartition_btn;
    @FXML public HBox ResultsPartition_btn;
    @FXML public HBox profile_btn;
    @FXML public HBox logout_btn;

    private HBox currentSelected;

    public ClientMenuController() {
        Model.getInstance().getControllerFactory().setClientMenuController(this);
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        this.currentSelected = HomePartition_btn;
    }

    private void handleSelected(HBox curr) {
        this.currentSelected.getStyleClass().clear();
        this.currentSelected.getStyleClass().add("mouse-moved");
        this.currentSelected = curr;
        this.currentSelected.getStyleClass().add("selected");
    }

    @FXML private void onLogout() {
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Request req = new LogoutRequest(Model.getInstance().getCurrConnectedEntity(), "true","/Logout");
        req.handleRequest(HttpMethods.POST);

        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    @FXML public void showHomeScreen() {
        onDashBoard();
    }

    @FXML public void showProfile() {

    }

    @FXML public void showSimulationDetails() {
        onSimulationDetails();
    }

    @FXML public void showSimulationExecute() {
        onExecution();
    }

    @FXML public void showSimulationRequests() {
        onRequests();
    }

    @FXML public void showSimulationResults() { onResults(); }

    private void onDashBoard() {
        handleSelected(HomePartition_btn);
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.HOME);
    }

    private void onSimulationDetails() {
        handleSelected(detailsPartition_btn);
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.SIMULATION_DETAILS);
    }

    private void onRequests() {
        handleSelected(RequestsPartition_btn);
        Model.getInstance().stopAllTasks();
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.REQUESTS);
    }

    private void onExecution() {
        handleSelected(ExecutePartition_btn);
        Model.getInstance().stopAllTasks();
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.EXECUTION);
    }

    private void onResults() {
        handleSelected(ResultsPartition_btn);
        Model.getInstance().stopAllTasks();
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.RESULTS);
        Model.getInstance().getControllerFactory().getExecutionsController().startAllTasks();
    }
}
