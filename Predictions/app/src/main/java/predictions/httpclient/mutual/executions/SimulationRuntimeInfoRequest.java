package predictions.httpclient.mutual.executions;

import com.google.gson.Gson;
import dto.request.mutual.executions.SimulationRuntimeInfoRequestDTO;
import dto.response.mutual.executions.SimulationRuntimeInfoResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class SimulationRuntimeInfoRequest implements Request {
    public SimulationRuntimeInfoResponseDTO getRuntimeInfo(String clientName, Integer requestID, Integer simulationID) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetRuntimeInfo",
                    new SimulationRuntimeInfoRequestDTO(clientName, requestID, simulationID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SimulationRuntimeInfoResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
