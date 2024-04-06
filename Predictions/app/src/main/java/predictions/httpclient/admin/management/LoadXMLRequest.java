package predictions.httpclient.admin.management;

import com.google.gson.Gson;
import dto.request.admin.management.LoadFileRequestDTO;
import dto.response.admin.management.LoadFileResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class LoadXMLRequest implements Request {
    public LoadFileResponseDTO loadXMLRequest(String path) {
        try {
            HttpRequest post = new Post();
            CloseableHttpResponse response = post.getResponse("/LoadSimulation", new LoadFileRequestDTO(path));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, LoadFileResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
