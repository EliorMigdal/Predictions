package predictions.views.Factories;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import predictions.controllers.admin.AdminController;
import predictions.controllers.client.ClientController;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.type.Interaction.LogoutRequest;
import predictions.views.Enums.AccountType;
import predictions.views.Enums.AdminMenuOptions;
import predictions.views.Enums.ClientMenuOptions;
import predictions.views.Enums.HttpMethods;

import java.io.IOException;
import java.util.Objects;

public class ViewFactory {
    private AccountType loginAccountType;
    // Client views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane simulationDetailsView;
    private AnchorPane requestsView;
    private AnchorPane executionView;
    private AnchorPane resultsView;
    // Admin views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane ManagementView;
    private AnchorPane AllocationsView;
    private AnchorPane ExecutionHistoryView;
    private AnchorPane ThreadsManagementView;

    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
    * Client Views Section
     */
    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if(dashboardView == null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/home/Dashboard.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getExecutionView() {
        if(executionView == null){
            try{
                executionView = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/execution/Execution.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return executionView;
    }

    public AnchorPane getSimulationDetailsView() {
        if(simulationDetailsView == null){
            try{
                simulationDetailsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/details/SimulationDetails.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return simulationDetailsView;
    }

    public AnchorPane getRequestsView() {
        if(requestsView == null){
            try{
                requestsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/requests/Requests.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return requestsView;
    }

    public AnchorPane getResultsView() {
        if(resultsView == null){
            try{
                resultsView = new FXMLLoader(getClass().getResource("/predictions/controllers/client/executions/resources/Executions.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultsView;
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader, " - Client");
    }

    /*
     * Admin Views Section
     */
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem(){
        return this.adminSelectedMenuItem;
    }

    public AnchorPane getThreadsManagementView() {
        if(ThreadsManagementView == null){
            try{
                ThreadsManagementView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/ThreadManagement/ThreadManagement.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ThreadsManagementView;
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader," - Admin");
    }

    public AnchorPane getManagementView() {
        if(ManagementView == null){
            try{
                ManagementView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/management/Management.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ManagementView;
    }

    public AnchorPane getAllocationsView() {

        if(AllocationsView == null){
            try{
                AllocationsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/allocations/Allocations.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AllocationsView;
    }

    public AnchorPane getExecutionHistoryView() {
        if(ExecutionHistoryView == null){
            try{
                ExecutionHistoryView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Views/execution_history/ExecutionHistory.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ExecutionHistoryView;
    }

    public void showLoginWindow () {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader, "");
    }

    private void createStage(FXMLLoader loader,String title) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Predictions" + title);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/applicationIcon.png")));
        stage.getIcons().add(icon);
        if(title.isEmpty()) {
            stage.setResizable(false);
        }
        else{
            stage.setOnCloseRequest(event -> {
                boolean isClient = title.equals(" - Admin");
                Request req = new LogoutRequest(Model.getInstance().getCurrConnectedEntity(), isClient ? "false" :"true", "/Logout");
                req.handleRequest(HttpMethods.POST);
            });
        }
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
