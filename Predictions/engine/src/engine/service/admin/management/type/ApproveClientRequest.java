package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;

public class ApproveClientRequest implements ManagementRequest {
    private EngineService engine;

    public ApproveClientRequest() {
    }

    public ApproveClientRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return true;
    }
}
