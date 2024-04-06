package engine.service.admin.management;

import engine.service.admin.AdminRequest;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;

public interface ManagementRequest extends AdminRequest {
    Object generateResponse(Object... args) throws XMLException, InitException;
}
