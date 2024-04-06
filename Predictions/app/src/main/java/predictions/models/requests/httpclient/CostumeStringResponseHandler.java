package predictions.models.requests.httpclient;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class CostumeStringResponseHandler implements HttpClientResponseHandler<String> {


    @Override
    public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        int status = response.getCode();
        HttpEntity entity = response.getEntity();

        try {
            if (status >= 200 && status < 300) {
                return EntityUtils.toString(entity);
            }
            else{
                return "";
            }
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }
}
