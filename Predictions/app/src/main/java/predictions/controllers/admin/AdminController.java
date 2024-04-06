package predictions.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem()
                .addListener(((observable, oldValue, newValue) -> {
            switch (newValue) {
                case MANAGEMENT:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getManagementView());
                    break;
                case ALLOCATIONS:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getAllocationsView());
                    break;
                case EXECUTION_HISTORY:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getExecutionHistoryView());
                    break;
                case THREADS_MANAGEMENT:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getThreadsManagementView());
                    break;
              default:
                    break;
            }
        }));
    }
}
