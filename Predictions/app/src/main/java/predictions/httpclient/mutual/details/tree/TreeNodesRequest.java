package predictions.httpclient.mutual.details.tree;

import com.google.gson.Gson;
import dto.request.mutual.details.tree.TreeDetailsRequestDTO;
import dto.response.mutual.details.tree.TreeDetailsResponseDTO;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Get;

import java.io.IOException;

public class TreeNodesRequest implements Request {
    public TreeDetailsResponseDTO getTreeNodes(String simulationName) {
        try {
            HttpRequest httpGet = new Get();
            CloseableHttpResponse response = httpGet.getResponse("/GetTreeDetails",
                    new TreeDetailsRequestDTO(simulationName));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            return new Gson().fromJson(jsonResponse, TreeDetailsResponseDTO.class);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
