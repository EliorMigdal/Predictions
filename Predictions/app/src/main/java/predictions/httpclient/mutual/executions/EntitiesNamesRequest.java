package predictions.httpclient.mutual.executions;

import com.google.gson.Gson;
import dto.request.mutual.executions.EntitiesNamesRequestDTO;
import dto.response.mutual.executions.EntitiesNamesDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class EntitiesNamesRequest implements Request {
    public EntitiesNamesDTO getEntitiesNames(String simulationName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetEntitiesNames",
                    new EntitiesNamesRequestDTO(simulationName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, EntitiesNamesDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
