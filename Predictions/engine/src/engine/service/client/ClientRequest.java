package engine.service.client;

import engine.service.Request;
import engine.service.exception.InitException;

public interface ClientRequest extends Request {
    Object generateResponse(Object... args) throws InitException;
}
