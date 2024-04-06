package predictions.models.requests.type.other;

import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.httpclient.BooleanResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SetPopulationRequest implements Request {

private final String entityName;
private final Integer newPopulation;
private final String endPoint;
    public SetPopulationRequest(String entityName, Integer newPopulation, String endPoint) {
        this.entityName = entityName;
        this.newPopulation = newPopulation;
        this.endPoint = endPoint;
    }


    @Override
    public String GET() {
        return null;
    }

    @Override
    public String POST() {
        return null;
    }

    @Override
    public Object PUT() {
        try(CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()){

            HttpClientResponseHandler<Boolean> responseHandler = new BooleanResponseHandler();
            URI url = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endPoint).build();
            HttpPut method = new HttpPut(url);
            List<BasicNameValuePair> reqData = new ArrayList<>();
            reqData.add( new BasicNameValuePair("entityName", this.entityName));
            reqData.add( new BasicNameValuePair("newPopulation", this.newPopulation.toString()));
            method.setEntity(new UrlEncodedFormEntity(reqData));
            return  httpClient.execute(method,responseHandler);
        } catch (Exception ex){
            throw new RuntimeException(ex);

        }
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
