package engine.version.types.requests;

import engine.client.request.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestsVersions {
    private final Map<Integer, Request> requestsVersion = new LinkedHashMap<>();
    private Integer latestVersion = 0;

    public Map<Integer, Request> getRequestsVersion() {
        return requestsVersion;
    }

    public void addNewRequest(Request newRequest) {
        requestsVersion.put(++latestVersion, newRequest);
    }
}
