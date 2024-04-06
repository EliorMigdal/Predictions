package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;

public class GetNumOfFinishedTasks implements ThreadPoolRequest {
    private EngineService engine;

    public GetNumOfFinishedTasks() {
    }

    public GetNumOfFinishedTasks(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return engine.getThreadPool().getCompletedTaskCount();
    }
}
