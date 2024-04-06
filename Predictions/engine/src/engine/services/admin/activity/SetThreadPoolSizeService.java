package engine.services.admin.activity;

import dto.request.admin.activity.SetThreadSizeRequestDTO;
import dto.response.admin.activity.SetThreadSizeResponseDTO;
import engine.Engine;
import engine.services.Service;

public class SetThreadPoolSizeService implements Service {
    public SetThreadSizeResponseDTO provideService(SetThreadSizeRequestDTO requestDTO, Engine engine) {
        if (engine.getThreadPool().getQueuedTaskCount() == 0 && engine.getThreadPool().getActiveThreadCount() == 0) {
            engine.getThreadPool().setThreadPoolExecutorSize(Integer.parseInt(requestDTO.getSize()));
            return new SetThreadSizeResponseDTO("Successfully updated thread pool size!", true);
        } else {
            return new SetThreadSizeResponseDTO("Thread pool is currently active!", false);
        }
    }
}
