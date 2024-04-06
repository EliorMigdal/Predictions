package predictions.models.logic.fxml.requests.handler;


import predictions.models.logic.fxml.requests.Request;

import java.io.IOException;

public interface RequestHandler {
    Object handleRequest(Request request, Object... args) throws IOException;
}
