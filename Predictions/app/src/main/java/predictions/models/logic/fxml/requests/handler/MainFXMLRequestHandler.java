package predictions.models.logic.fxml.requests.handler;

import javafx.fxml.FXMLLoader;
import predictions.models.logic.fxml.requests.Request;
import predictions.models.logic.fxml.requests.runtime.RunTimeRequestHandler;
import predictions.models.logic.fxml.requests.runtime.RuntimeRequest;

import java.io.IOException;

public class MainFXMLRequestHandler implements RequestHandler{

    public MainFXMLRequestHandler() {

    }

    @Override
    public Object handleRequest(Request request, Object... args) throws IOException {
        if (request instanceof RuntimeRequest) {
            return new RunTimeRequestHandler(new FXMLLoader()).handleRequest(request, args);
        } else {
            return null;
        }
    }
}
