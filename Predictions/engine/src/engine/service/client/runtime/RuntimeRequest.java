package engine.service.client.runtime;

import engine.service.client.ClientRequest;

public interface RuntimeRequest extends ClientRequest {
    Object generateResponse(Object... args);
}
