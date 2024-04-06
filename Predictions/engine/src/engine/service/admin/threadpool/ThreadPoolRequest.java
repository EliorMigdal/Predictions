package engine.service.admin.threadpool;

import engine.service.admin.AdminRequest;

public interface ThreadPoolRequest extends AdminRequest {
    Object generateResponse(Object... args);
}
