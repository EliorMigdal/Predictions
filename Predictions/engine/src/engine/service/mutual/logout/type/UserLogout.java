package engine.service.mutual.logout.type;

import engine.EngineService;
import engine.service.mutual.logout.LogoutRequest;

public class UserLogout implements LogoutRequest {
    private EngineService engine;

    public UserLogout() {
    }

    public UserLogout(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        engine.getUserManager().removeClient(clientName);
        return true;
    }
}
