package predictions.httpclient.admin.executions;

import com.google.gson.Gson;
import dto.request.admin.executions.ExecutedSimulationsRequestDTO;
import dto.response.admin.execution.ExecutedSimulationsResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class ExecutedSimulationsRequest implements Request {
    public ExecutedSimulationsResponseDTO getExecutedSimulations(Integer version) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetExecutedSimulations",
                    new ExecutedSimulationsRequestDTO(version));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, ExecutedSimulationsResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
