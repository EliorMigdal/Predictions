package predictions.models.logic.fxml.requests.runtime;



import predictions.models.logic.fxml.requests.Request;

import java.io.IOException;

public interface RuntimeRequest extends Request {
    Object generateResponse(Object... args) throws IOException;
}
