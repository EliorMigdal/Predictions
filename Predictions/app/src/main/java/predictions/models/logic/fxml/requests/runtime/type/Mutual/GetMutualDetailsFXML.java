package predictions.models.logic.fxml.requests.runtime.type.Mutual;

import javafx.fxml.FXMLLoader;
import predictions.controllers.admin.management.ManagementController;
import predictions.controllers.client.details.SimulationDetailsController;
import predictions.models.logic.fxml.requests.FXMLResponseObj;
import predictions.models.logic.fxml.requests.runtime.RuntimeRequest;

import java.io.IOException;
import java.net.URL;

public class GetMutualDetailsFXML implements RuntimeRequest {

    private FXMLLoader fxmlLoader;

    public GetMutualDetailsFXML(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public GetMutualDetailsFXML() {
    }
    @Override
    public Object generateResponse(Object... args) throws IOException {
        URL currURLConstant = null;
        SimulationDetailsController detailsController = null;
        ManagementController managementController = null;
        for (Object argument : args) {
            if (argument instanceof URL) {
                currURLConstant = (URL) argument;
            }
            if(argument instanceof SimulationDetailsController){
                detailsController = (SimulationDetailsController) argument;
            }
            else if (argument instanceof ManagementController){
                managementController = (ManagementController) argument;
            }
        }
        if(currURLConstant != null) {
            this.fxmlLoader.setLocation(currURLConstant);
            if(detailsController != null){
                this.fxmlLoader.setResources(detailsController);
            }
            else if (managementController != null){
                this.fxmlLoader.setResources(managementController);
            }
            return  new FXMLResponseObj(this.fxmlLoader.load(),this.fxmlLoader.getController());

        }



        return null;
    }
}
