package predictions.httpclient.requests.type;

import com.google.gson.Gson;
import dto.DTO;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import predictions.httpclient.requests.HttpRequest;
import predictions.models.Model;
import predictions.views.Enums.DomainUrl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Post implements HttpRequest {
    @Override
    public CloseableHttpResponse getResponse(String endpoint, DTO requestObject) {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(Model.getInstance().getHttpClientManager())
                .setConnectionManagerShared(true)
                .build()) {
            String jsonObject = new Gson().toJson(requestObject);
            URIBuilder uriBuilder = new URIBuilder(DomainUrl.DOMAIN_URL.getUrl() + endpoint);

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            List<BasicNameValuePair> requestData = new ArrayList<>();
            requestData.add(new BasicNameValuePair("json", jsonObject));
            httpPost.setEntity(new UrlEncodedFormEntity(requestData));

            return httpClient.execute(httpPost);
        } catch (URISyntaxException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}