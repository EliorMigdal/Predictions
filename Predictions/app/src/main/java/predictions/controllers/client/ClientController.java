package predictions.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener(((observable, oldValue, newValue) ->{
            switch (newValue) {
                case SIMULATION_DETAILS:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getSimulationDetailsView());
                    break;
                case REQUESTS:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getRequestsView());
                    break;
                case EXECUTION:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getExecutionView());
                    break;
                case RESULTS:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getResultsView());
                    break;
                default:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                    break;
            }
        }));
    }
}
