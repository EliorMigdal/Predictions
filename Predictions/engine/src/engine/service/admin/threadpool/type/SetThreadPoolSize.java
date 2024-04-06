package engine.service.admin.threadpool.type;

import engine.EngineService;
import engine.service.admin.threadpool.ThreadPoolRequest;

public class SetThreadPoolSize implements ThreadPoolRequest {
    private EngineService engine;

    public SetThreadPoolSize() {
    }

    public SetThreadPoolSize(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String threadPoolSize = args[0].toString();

        if (engine.getThreadPool().getQueuedTaskCount() == 0 && engine.getThreadPool().getActiveThreadCount() == 0) {
            engine.getThreadPool().setThreadPoolExecutorSize(Integer.parseInt(threadPoolSize));
            return "Successfully updated thread pool size!";
        } else {
            return "Thread pool is currently active!";
        }
    }
}
