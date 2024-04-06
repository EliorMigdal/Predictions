package engine.service.client.user;

import engine.service.client.ClientRequest;
import engine.service.exception.InitException;

public interface UserRequest extends ClientRequest {
    Object generateResponse(Object... args) throws InitException;
}
