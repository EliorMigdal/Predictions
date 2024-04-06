package engine.service.client.init;

import engine.service.client.ClientRequest;
import engine.service.exception.InitException;

public interface SimulationInitRequest extends ClientRequest {
    Object generateResponse(Object... args) throws InitException;
}