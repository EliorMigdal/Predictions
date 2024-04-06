package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;

public class GetNumOfBusyThreads implements ThreadPoolRequest {
    private EngineService engine;

    public GetNumOfBusyThreads() {
    }

    public GetNumOfBusyThreads(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return engine.getThreadPool().getActiveThreadCount();
    }
}
