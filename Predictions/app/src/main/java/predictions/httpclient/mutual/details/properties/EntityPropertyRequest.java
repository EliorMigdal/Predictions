package predictions.httpclient.mutual.details.properties;

import com.google.gson.Gson;
import dto.request.mutual.details.properties.EntityPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class EntityPropertyRequest implements Request {
    public PropertyResponseDTO getEntityProperty(String simulationName, String entityName, String propertyName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetEntityProperty",
                    new EntityPropertyRequestDTO(simulationName, entityName, propertyName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, PropertyResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}