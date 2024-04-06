package predictions.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.type.Interaction.LogoutRequest;
import predictions.views.Enums.AdminMenuOptions;
import predictions.views.Enums.HttpMethods;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    @FXML
    private HBox Management_btn;

    @FXML
    private HBox thread_management_btn;

    @FXML
    public HBox allocations_btn;

    @FXML
    public HBox executeHistory_btn;

    @FXML
    public HBox profile_btn;

    @FXML
    public HBox logout_btn;

    private HBox currentSelected;

    private void handleSelected(HBox curr){
        this.currentSelected.getStyleClass().clear();
        this.currentSelected.getStyleClass().add("mouse-moved");
        this.currentSelected = curr;
        this.currentSelected.getStyleClass().add("selected");
    }


    @FXML
    void onLogout(MouseEvent event) {
        Stage stage = (Stage) logout_btn.getScene().getWindow();

        Request req = new LogoutRequest(Model.getInstance().getCurrConnectedEntity(), "false","/Logout");
        req.handleRequest(HttpMethods.POST);

        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
    @FXML
    void showAllocations(MouseEvent event) {
        onAllocations();
    }

    @FXML
    void showExecutionHistory(MouseEvent event) {
        onExecutionHistory();
    }

    @FXML
    void showManagement(MouseEvent event) {
        onManagement();
    }

    @FXML
    void showThreadManagement(MouseEvent event) {onThreadsManagement();}

    @FXML
    void showProfile(MouseEvent event) {

    }
    private void onManagement(){
        handleSelected(Management_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGEMENT);
    }
    private void onAllocations(){
        handleSelected(allocations_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ALLOCATIONS);
    }
    private void onExecutionHistory(){
        handleSelected(executeHistory_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.EXECUTION_HISTORY);
    }

    private  void onThreadsManagement() {
        handleSelected(thread_management_btn);
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.THREADS_MANAGEMENT);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.currentSelected = Management_btn;

    }
}
