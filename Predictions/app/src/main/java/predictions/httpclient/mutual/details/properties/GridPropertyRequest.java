package predictions.httpclient.mutual.details.properties;

import com.google.gson.Gson;
import dto.request.mutual.details.properties.GridPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class GridPropertyRequest implements Request {
    public PropertyResponseDTO getGridProperty(String simulationName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetGridProperty",
                    new GridPropertyRequestDTO(simulationName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, PropertyResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
