package predictions.models.requests.type.other;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.net.URIBuilder;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.httpclient.StringResponseHandler;
import predictions.views.Enums.DomainUrl;
import predictions.views.Enums.HttpMethods;

import java.io.IOException;
import java.net.URISyntaxException;

public class SetThreadPoolSize implements Request {
    private final String threadPoolSize;
    private final String endpoint;

    public SetThreadPoolSize(String threadPoolSize, String endpoint) {
        this.threadPoolSize = threadPoolSize;
        this.endpoint = endpoint;
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

            URIBuilder uriBuilder = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endpoint);
            uriBuilder.addParameter("size", this.threadPoolSize);
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            return httpClient.execute(httpPost, responseHandler);
        } catch (IOException | URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
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
