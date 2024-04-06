package engine.service.mutual.logout.type;

import engine.EngineService;
import engine.service.mutual.logout.LogoutRequest;

public class AdminLogout implements LogoutRequest {
    private EngineService engine;

    public AdminLogout() {
    }

    public AdminLogout(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        engine.getUserManager().removeAdmin();
        return true;
    }
}
