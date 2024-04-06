package predictions.httpclient.admin.allocations;

import com.google.gson.Gson;
import dto.request.admin.allocations.ApproveRequestDTO;
import dto.response.mutual.general.EmptyResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class ApproveRequest implements Request {
    public EmptyResponseDTO approveRequest(String clientName, Integer requestID) {
        try {
            HttpRequest approveRequest = new Post();
            CloseableHttpResponse response = approveRequest.getResponse("/ApproveClientRequest",
                    new ApproveRequestDTO(clientName, requestID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, EmptyResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
