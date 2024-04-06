package engine.service.admin;

import engine.service.Request;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;

public interface AdminRequest extends Request {
    Object generateResponse(Object... args) throws XMLException, InitException;
}
