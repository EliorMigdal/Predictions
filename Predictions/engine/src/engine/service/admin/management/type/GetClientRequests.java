package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;

public class GetClientRequests implements ManagementRequest {
    private EngineService engine;

    public GetClientRequests() {
    }

    public GetClientRequests(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return null;
    }
}
