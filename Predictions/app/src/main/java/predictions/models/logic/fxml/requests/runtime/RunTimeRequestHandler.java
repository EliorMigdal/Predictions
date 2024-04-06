package predictions.models.logic.fxml.requests.runtime;

import javafx.fxml.FXMLLoader;
import predictions.models.logic.fxml.requests.handler.RequestHandler;
import predictions.models.logic.fxml.requests.runtime.type.Admin.GetAdminResultsFXML;
import predictions.models.logic.fxml.requests.runtime.type.Client.GetClientExecuteFXML;
import predictions.models.logic.fxml.requests.runtime.type.Client.GetClientRequestsFXML;
import predictions.models.logic.fxml.requests.runtime.type.Client.GetClientResultFXML;
import predictions.models.logic.fxml.requests.runtime.type.Mutual.GetMutualDetailsFXML;
import predictions.models.logic.fxml.requests.runtime.type.Mutual.GetMutualResultsFXML;
import predictions.models.logic.fxml.requests.Request;


import java.io.IOException;

public class RunTimeRequestHandler implements RequestHandler {
    private final FXMLLoader fxmlLoader;

    public RunTimeRequestHandler(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }


    @Override
    public Object handleRequest(Request request, Object... args) throws IOException {
        if(request instanceof GetMutualResultsFXML){
            return new GetMutualResultsFXML(fxmlLoader).generateResponse(args);
        } else if (request instanceof GetMutualDetailsFXML){
            return new GetMutualDetailsFXML(fxmlLoader).generateResponse(args);
        } else if (request instanceof GetClientExecuteFXML) {
            return new GetClientExecuteFXML(fxmlLoader).generateResponse(args);
        } else if (request instanceof GetClientRequestsFXML){
                return new GetClientRequestsFXML(fxmlLoader).generateResponse(args);
        } else if (request instanceof GetClientResultFXML){
            return new GetClientResultFXML(fxmlLoader).generateResponse(args);
        } else if (request instanceof GetAdminResultsFXML){
            return new GetAdminResultsFXML(fxmlLoader).generateResponse(args);
        }
        return null;
    }
}
