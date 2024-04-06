package predictions.httpclient.admin.general;

import com.google.gson.Gson;
import dto.request.admin.general.RequestsSizeRequestDTO;
import dto.response.admin.general.RequestsSizeResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class RequestsSizeRequest implements Request {
    public RequestsSizeResponseDTO getNumOfRequests(String clientName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetNumOfRequests",
                    new RequestsSizeRequestDTO(clientName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, RequestsSizeResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
