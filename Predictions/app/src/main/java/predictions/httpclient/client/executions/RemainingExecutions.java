package predictions.httpclient.client.executions;

import com.google.gson.Gson;
import dto.request.client.executions.RemainingExecutionsDTO;
import dto.response.client.executions.RemainingExecutionsResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class RemainingExecutions implements Request {
    public RemainingExecutionsResponseDTO getNumOfRemaining(String clientName, Integer requestID) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetRemainingExecutions",
                    new RemainingExecutionsDTO(clientName, requestID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, RemainingExecutionsResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
