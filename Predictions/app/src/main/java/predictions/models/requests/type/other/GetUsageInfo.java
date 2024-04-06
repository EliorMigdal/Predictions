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
import dto.response.admin.activity.ClientUsageResponseOldDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GetUsageInfo implements Request {
    private final String endpoint;

    public GetUsageInfo(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Object GET() {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()) {

            HttpClientResponseHandler<String> responseHandler = new StringResponseHandler();
            URIBuilder uriBuilder = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endpoint);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            String jsonResponse = httpClient.execute(httpGet, responseHandler);

            if (jsonResponse.isEmpty()) {
                return new ArrayList<>();
            } else {
                Gson gsonObject = new Gson();
                Type arrayType = new TypeToken<ArrayList<ClientUsageResponseOldDTO>>(){}.getType();
                return gsonObject.fromJson(jsonResponse, arrayType);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
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
