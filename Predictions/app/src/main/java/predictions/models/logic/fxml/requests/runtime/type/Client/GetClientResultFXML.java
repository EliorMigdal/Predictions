package predictions.models.logic.fxml.requests.runtime.type.Client;

import javafx.fxml.FXMLLoader;
import predictions.models.logic.fxml.requests.FXMLResponseObj;
import predictions.models.logic.fxml.requests.runtime.RuntimeRequest;

import java.io.IOException;
import java.net.URL;

public class GetClientResultFXML implements RuntimeRequest {
    private FXMLLoader fxmlLoader;

    public GetClientResultFXML(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public GetClientResultFXML() {
        this.fxmlLoader = new FXMLLoader();

    }

    @Override
    public Object generateResponse(Object... args) throws IOException {
        URL currURLConstant = null;
        for (Object argument : args) {
            if (argument instanceof URL) {
                currURLConstant = (URL) argument;
            }
        }
        if(currURLConstant != null ) {
            this.fxmlLoader.setLocation(currURLConstant);
            return new FXMLResponseObj(this.fxmlLoader.load(), this.fxmlLoader.getController());

        }

        return null;
    }
}
