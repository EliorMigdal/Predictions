package predictions.httpclient.admin.activity;

import com.google.gson.Gson;
import dto.request.admin.activity.ClientUsageRequestDTO;
import dto.response.admin.activity.ClientUsageResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class ClientUsageRequest implements Request {
    public ClientUsageResponseDTO getClientUsage(Integer version) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetUsageInfo",
                    new ClientUsageRequestDTO(version));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, ClientUsageResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
