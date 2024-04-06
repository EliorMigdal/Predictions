package predictions.httpclient.client.init;

import com.google.gson.Gson;
import dto.request.client.init.EnvironmentPropertiesDTO;
import dto.response.client.init.AllEnvPropertiesDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class GetEnvironmentProperties implements Request {
    public AllEnvPropertiesDTO getEnvironmentProperties(String simulationName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetEnvironmentProperties",
                    new EnvironmentPropertiesDTO(simulationName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, AllEnvPropertiesDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
