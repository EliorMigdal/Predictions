package engine.services.admin.activity;

import dto.request.mutual.general.EmptyRequestDTO;
import dto.response.admin.activity.ThreadPoolStatusResponseDTO;
import engine.Engine;
import engine.services.Service;

public class ThreadPoolStatusService implements Service {
    public ThreadPoolStatusResponseDTO provideService(EmptyRequestDTO requestDTO, Engine engine) {
        ThreadPoolStatusResponseDTO poolStatus = new ThreadPoolStatusResponseDTO();
        poolStatus.setCurrentlyRunning(((Integer) engine.getThreadPool().getActiveThreadCount()).toString());
        poolStatus.setFinished(((Integer) engine.getThreadPool().getCompletedTaskCount()).toString());
        poolStatus.setQueueSize(((Integer) engine.getThreadPool().getQueuedTaskCount()).toString());

        return poolStatus;
    }
}
