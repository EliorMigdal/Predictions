package predictions.models.requests.type.other;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.httpclient.StringResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class LoadXMLRequest implements Request {
    private final String path;
    private final String endPoint;

    public LoadXMLRequest(String path, String endPoint) {
        this.path = path;
        this.endPoint = endPoint;
    }

    @Override
    public Object GET() {
        return null;
    }

    @Override
    public Object POST() {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()) {

            HttpClientResponseHandler<String> responseHandler = new StringResponseHandler();
            URI url = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endPoint).build();
            HttpPost method = new HttpPost(url);
            List<BasicNameValuePair> reqData = new ArrayList<>();
            reqData.add(new BasicNameValuePair("Path", this.path));
            method.setEntity(new UrlEncodedFormEntity(reqData));
            return httpClient.execute(method, responseHandler);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Object PUT() {
      return null;
    }

    @Override
    public Object handleRequest(HttpMethods method) {
        switch (method) {
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
