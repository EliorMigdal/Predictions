package predictions.httpclient.client.executions;

import com.google.gson.Gson;
import dto.request.client.executions.SimulationStatusDTO;
import dto.response.client.executions.SimulationStatusResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class SimulationStatus implements Request {
    public SimulationStatusResponseDTO getSimulationStatus(String clientName, Integer requestID, Integer simulationID) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetSimulationStatus",
                    new SimulationStatusDTO(clientName, requestID, simulationID));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SimulationStatusResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
