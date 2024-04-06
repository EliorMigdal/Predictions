package predictions.httpclient.mutual.executions;

import com.google.gson.Gson;
import dto.request.mutual.executions.PropertyResultsRequestDTO;
import dto.response.mutual.executions.PropertyResultResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class PropertyResultsRequest implements Request {
    public PropertyResultResponseDTO getPropertyResults(String clientName, Integer requestID, Integer simulationID,
                                                        String entityName, String propertyName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetPropertyResults",
                    new PropertyResultsRequestDTO(clientName, requestID, simulationID, entityName, propertyName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, PropertyResultResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
