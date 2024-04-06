package predictions.models.requests.type.other;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.httpclient.BooleanResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;
import dto.request.client.ClientRequestNoDateDTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.net.URI;
import java.util.ArrayList;

public class SubmitNewClientRequest implements Request {
    private final String endPoint;
    private final String clientName;
    private final String simulationName;
    private final String numOfExecutions;
    private final ArrayList<TerminationInfoDTO> terminations;

    public SubmitNewClientRequest(String endPoint, String clientName, String simulationName, String numOfExecutions, ArrayList<TerminationInfoDTO> terminations) {
        this.endPoint = endPoint;
        this.clientName = clientName;
        this.simulationName = simulationName;
        this.numOfExecutions = numOfExecutions;
        this.terminations = terminations;
    }

    @Override
    public Object GET() {
        return null;
    }

    @Override
    public Object POST() {
        try(CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()){
            HttpClientResponseHandler<Boolean> responseHandler = new BooleanResponseHandler();
            URI url = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endPoint).build();
            HttpPost method = new HttpPost(url);

            ClientRequestNoDateDTO data = new ClientRequestNoDateDTO(this.clientName, this.simulationName, this.numOfExecutions, this.terminations);
            Gson gson = new Gson();
            String json = gson.toJson(data);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            method.setEntity(entity);
            return  httpClient.execute(method,responseHandler);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
