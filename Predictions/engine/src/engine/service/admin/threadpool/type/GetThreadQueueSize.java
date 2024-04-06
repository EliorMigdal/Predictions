package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;

public class GetThreadQueueSize implements ThreadPoolRequest {
    private EngineService engine;

    public GetThreadQueueSize() {
    }

    public GetThreadQueueSize(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return engine.getThreadPool().getQueuedTaskCount();
    }
}
