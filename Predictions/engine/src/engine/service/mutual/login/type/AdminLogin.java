package engine.service.mutual.login.type;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.service.mutual.login.LoginRequest;

public class AdminLogin implements LoginRequest {
    private EngineService engine;

    public AdminLogin() {
    }

    public AdminLogin(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws AdminAlreadyLogged {
        String adminName = (String) args[0];
        engine.getUserManager().addAdmin(adminName);
        return true;
    }
}
