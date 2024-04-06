package predictions.httpclient.mutual.simulations;

import com.google.gson.Gson;
import dto.request.mutual.simulations.SimulationsListRequestDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class SimulationsListRequest implements Request {
    public SimulationsListResponseDTO getSimulationsList(Integer simulationsVersion) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetSimulationsList",
                    new SimulationsListRequestDTO(simulationsVersion));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, SimulationsListResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
