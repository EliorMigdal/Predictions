package engine.threadpool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPool {
    private ExecutorService executorService;
    private Integer threadPoolSize;
    private final Map<Integer, Future<?>> taskMap = new ConcurrentHashMap<>();

    private final AtomicInteger activeThreadCount = new AtomicInteger(0);
    private final AtomicInteger queuedTaskCount = new AtomicInteger(0);
    private final AtomicInteger completedTaskCount = new AtomicInteger(0);

    public CustomThreadPool() {
        executorService = Executors.newFixedThreadPool(3);
    }

    public void setThreadPoolExecutorSize(int newThreadCount) {
        if (newThreadCount > 0) {
            if (newThreadCount != this.threadPoolSize) {
                executorService.shutdown();
                executorService = Executors.newFixedThreadPool(newThreadCount);
                this.threadPoolSize = newThreadCount;
            }
        }
    }

    public void execute(int simulationID, Runnable task) {
        queuedTaskCount.incrementAndGet();
        activeThreadCount.incrementAndGet();

        Future<?> future = executorService.submit(() -> {
            try {
                task.run();
            } finally {
                activeThreadCount.decrementAndGet();
                completedTaskCount.incrementAndGet();
            }
        });

        queuedTaskCount.decrementAndGet();
        taskMap.put(simulationID, future);
    }

    public int getActiveThreadCount() {
        return activeThreadCount.get();
    }

    public int getQueuedTaskCount() {
        return queuedTaskCount.get();
    }

    public int getCompletedTaskCount() {
        return completedTaskCount.get();
    }

    public void increaseNumOfQueued() {
        this.queuedTaskCount.incrementAndGet();
    }

    public void pauseTask(Integer simulationID) {
        Future<?> future = taskMap.get(simulationID);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(simulationID);
        }
    }
}
