package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;

public class DeclineClientRequest implements ManagementRequest {
    private EngineService engine;

    public DeclineClientRequest() {
    }

    public DeclineClientRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return true;
    }
}
