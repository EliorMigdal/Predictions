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
import predictions.models.requests.httpclient.CostumeStringResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;
import dto.response.mutual.simulations.SimulationInfoDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;


public class ViewSimulationsListRequest implements Request {
    private final String endPoint;

    public ViewSimulationsListRequest(String endPoint) {
        this.endPoint = endPoint;
    }


    @Override
    public Object GET() {
        try(CloseableHttpClient httpClient = HttpClients.custom()
                   .setConnectionManager(Model.getInstance().getHttpClientManager())
                   .setConnectionManagerShared(true)
                   .build()){
            Gson jsonObj = new Gson();
            HttpClientResponseHandler<String> responseHandler = new CostumeStringResponseHandler();
            URI url = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endPoint).build();
            HttpGet method = new HttpGet(url);
            String jsonResponse = httpClient.execute(method, responseHandler);
            if (jsonResponse.isEmpty()) {
                return new HashMap<>();
            } else {
                Type mapType = new TypeToken<ArrayList<SimulationInfoDTO>>(){}.getType();
                return jsonObj.fromJson(jsonResponse, mapType);
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
        switch (method){
            case GET:
                return GET();
            case POST:
                return POST();
            case PUT:
                return PUT();
            default:
                return "";
        }
    }
}
