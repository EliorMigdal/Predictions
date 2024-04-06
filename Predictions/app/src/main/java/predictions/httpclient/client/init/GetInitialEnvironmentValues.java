package predictions.httpclient.client.init;

import com.google.gson.Gson;
import dto.request.client.init.InitialValuesDTO;
import dto.response.client.init.InitialEnvironmentsResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class GetInitialEnvironmentValues implements Request {
    public InitialEnvironmentsResponseDTO getInitialEnvironmentValue(String clientName, Integer requestID,
                                                                Integer simulationID) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetInitialEnvironmentValues",
                    new InitialValuesDTO(clientName, requestID, simulationID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, InitialEnvironmentsResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
