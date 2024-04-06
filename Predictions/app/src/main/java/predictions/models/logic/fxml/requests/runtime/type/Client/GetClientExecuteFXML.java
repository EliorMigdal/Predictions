package predictions.models.logic.fxml.requests.runtime.type.Client;

import javafx.fxml.FXMLLoader;
import predictions.controllers.client.initialize.InitializationController;
import predictions.models.logic.fxml.requests.FXMLResponseObj;
import predictions.models.logic.fxml.requests.runtime.RuntimeRequest;

import java.io.IOException;
import java.net.URL;

public class GetClientExecuteFXML implements RuntimeRequest {
    private FXMLLoader fxmlLoader;

    public GetClientExecuteFXML(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public GetClientExecuteFXML() {
        this.fxmlLoader = new FXMLLoader();

    }

    @Override
    public Object generateResponse(Object... args) throws IOException {
        URL currURLConstant = null;
        InitializationController executeController = null;
        for (Object argument : args) {
            if (argument instanceof URL) {
                currURLConstant = (URL) argument;
            }
            if(argument instanceof InitializationController)
            {
                executeController = (InitializationController) argument;
            }
        }

        if(currURLConstant != null ) {
            this.fxmlLoader.setLocation(currURLConstant);
            if(executeController != null) {
                this.fxmlLoader.setResources(executeController);
            }
            return new FXMLResponseObj(this.fxmlLoader.load(), this.fxmlLoader.getController());

        }

        return null;
    }


}
