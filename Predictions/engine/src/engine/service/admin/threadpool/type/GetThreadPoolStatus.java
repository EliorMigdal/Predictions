package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;
import dto.response.admin.activity.ThreadPoolStatusResponseDTO;

public class GetThreadPoolStatus implements ThreadPoolRequest {
    private EngineService engine;

    public GetThreadPoolStatus() {
    }

    public GetThreadPoolStatus(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        ThreadPoolStatusResponseDTO poolStatus = new ThreadPoolStatusResponseDTO();
        poolStatus.setCurrentlyRunning(((Integer) engine.getThreadPool().getActiveThreadCount()).toString());
        poolStatus.setFinished(((Integer) engine.getThreadPool().getCompletedTaskCount()).toString());
        poolStatus.setQueueSize(((Integer) engine.getThreadPool().getQueuedTaskCount()).toString());

        return poolStatus;
    }
}
