package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;

public class AdminGetEntitiesInfo implements ManagementRequest {
    private EngineService engine;

    public AdminGetEntitiesInfo() {
    }

    public AdminGetEntitiesInfo(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws XMLException, InitException {
        return null;
    }
}
