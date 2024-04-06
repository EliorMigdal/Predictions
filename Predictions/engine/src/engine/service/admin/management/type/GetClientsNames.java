package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;

import java.util.ArrayList;

public class GetClientsNames implements ManagementRequest {
    private EngineService engine;

    public GetClientsNames() {
    }

    public GetClientsNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return new ArrayList<>(engine.getUserManager().getClientsNames());
    }
}
