package predictions.models.requests.type.other;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.net.URIBuilder;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.httpclient.StringResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;
import dto.response.mutual.executions.PropertyResultResponseDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminGetPropertyInfo implements Request {
    private final String clientName;
    private final String requestID;
    private final String simulationName;
    private final String simulationID;
    private final String entityName;
    private final String propertyName;

    public AdminGetPropertyInfo(String clientName, String requestID, String simulationName,
                                String simulationID, String entityName, String propertyName) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.simulationID = simulationID;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public Object GET() {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()) {

            HttpClientResponseHandler<String> responseHandler = new StringResponseHandler();
            URIBuilder uriBuilder = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() +
                    "/adminGetPropertyInfo");
            uriBuilder.addParameter("client", clientName);
            uriBuilder.addParameter("request", requestID);
            uriBuilder.addParameter("simulationName", simulationName);
            uriBuilder.addParameter("simulationID", simulationID);
            uriBuilder.addParameter("entity", entityName);
            uriBuilder.addParameter("property", propertyName);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            String jsonResponse = httpClient.execute(httpGet, responseHandler);

            if (jsonResponse.isEmpty()) {
                return new ArrayList<>();
            } else {
                Gson gsonObject = new Gson();
                Type arrayType = new TypeToken<PropertyResultResponseDTO>(){}.getType();
                return gsonObject.fromJson(jsonResponse, arrayType);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Object POST() {
        return null;
    }

    @Override
    public Object PUT() {
        return null;
    }

    @Override
    public Object handleRequest(HttpMethods method) {
        return null;
    }
}
