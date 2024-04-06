package predictions.httpclient.mutual.executions;

import com.google.gson.Gson;
import dto.request.mutual.executions.PopulationResultsRequestDTO;
import dto.response.mutual.executions.PopulationResultsResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class PopulationResultsRequest implements Request {
    public PopulationResultsResponseDTO getPopulationResults(String clientName, Integer requestID, Integer simulationID) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetPopulationResults",
                    new PopulationResultsRequestDTO(clientName, requestID, simulationID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, PopulationResultsResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
