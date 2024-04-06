package engine.service.client.runtime.type;

import engine.EngineService;
import engine.service.client.runtime.RuntimeRequest;

public class RunSimulation implements RuntimeRequest {
    private EngineService engine;

    public RunSimulation() {
    }

    public RunSimulation(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return null;
    }
}