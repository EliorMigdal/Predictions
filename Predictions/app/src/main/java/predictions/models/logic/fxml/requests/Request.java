package predictions.models.logic.fxml.requests;

import java.io.IOException;

public interface Request {
    Object generateResponse(Object... args) throws IOException;
}

