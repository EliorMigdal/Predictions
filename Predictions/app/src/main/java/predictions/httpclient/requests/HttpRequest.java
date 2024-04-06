package predictions.httpclient.requests;

import dto.DTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

public interface HttpRequest {
    CloseableHttpResponse getResponse(String endpoint, DTO requestObject);
}
