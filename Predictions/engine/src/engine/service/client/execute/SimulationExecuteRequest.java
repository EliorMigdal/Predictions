package engine.service.client.execute;

import engine.service.client.ClientRequest;

public interface SimulationExecuteRequest extends ClientRequest {
    Object generateResponse(Object... args);
}
