package predictions.httpclient.client.init;

import com.google.gson.Gson;
import dto.request.client.init.SetEnvValueDTO;
import dto.response.client.init.SetEnvValueResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class SetEnvironmentValue implements Request {
    public SetEnvValueResponseDTO setEnvironmentValue(String clientName, Integer requestID, Integer simulationID,
                                                    String propertyName, String value, Boolean setRandomly) {
        try {
            HttpRequest setEnvValue = new Post();
            CloseableHttpResponse response = setEnvValue.getResponse("/SetEnvironmentValue",
                    new SetEnvValueDTO(clientName, requestID, simulationID, propertyName, value, setRandomly));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SetEnvValueResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
