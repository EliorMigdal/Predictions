package predictions.httpclient.client.init;

import com.google.gson.Gson;
import dto.request.client.init.CreateExecutionDTO;
import dto.response.client.init.NewExecutionDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class NewExecution implements Request {
    public NewExecutionDTO createNewExecution(String clientName, Integer requestID, String simulationName) {
        try {
            HttpRequest createExecution = new Post();
            CloseableHttpResponse response = createExecution.getResponse("/CreateNewExecution",
                    new CreateExecutionDTO(clientName, requestID, simulationName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, NewExecutionDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
