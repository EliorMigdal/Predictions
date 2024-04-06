package predictions.httpclient.admin.activity;

import com.google.gson.Gson;
import dto.request.admin.activity.SetThreadSizeRequestDTO;
import dto.response.admin.activity.SetThreadSizeResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.io.IOException;

public class SetThreadPoolSizeRequest implements Request {
    public SetThreadSizeResponseDTO setThreadPoolSize(String size) {
        try {
            HttpRequest setPoolSize = new Post();
            CloseableHttpResponse response = setPoolSize.getResponse("/SetThreadPoolSize",
                    new SetThreadSizeRequestDTO(size));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SetThreadSizeResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
