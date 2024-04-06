package predictions.models.requests.httpclient;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class BooleanResponseHandler implements HttpClientResponseHandler<Boolean> {

    @Override
    public Boolean handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        int status = response.getCode();
        HttpEntity entity = response.getEntity();
        try {
            return status >= 200 && status < 300;
        } finally{
            EntityUtils.consumeQuietly(entity);
        }

    }
}
