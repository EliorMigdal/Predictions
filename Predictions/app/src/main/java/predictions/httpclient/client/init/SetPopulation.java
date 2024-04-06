package predictions.httpclient.client.init;

import com.google.gson.Gson;
import dto.request.client.init.SetPopulationDTO;
import dto.response.client.init.SetPopulationResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class SetPopulation implements Request {
    public SetPopulationResponseDTO setPopulationSize(String clientName, Integer requestID, Integer simulationID,
                                                      String entityName, Integer population) {
        try {
            HttpRequest setPopulationSize = new Post();
            CloseableHttpResponse response = setPopulationSize.getResponse("/SetPopulation",
                    new SetPopulationDTO(clientName, requestID, simulationID, entityName, population));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SetPopulationResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
