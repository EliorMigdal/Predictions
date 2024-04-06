package predictions.httpclient.admin.allocations;

import com.google.gson.Gson;
import dto.request.admin.allocations.GetRequestsInfoDTO;
import dto.response.admin.allocations.RequestsInfoDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class GetRequestsInfo implements Request {
    public RequestsInfoDTO getRequestsInfo(Integer version) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetClientsRequests",
                    new GetRequestsInfoDTO(version));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, RequestsInfoDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
