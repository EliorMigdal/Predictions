package engine.service.mutual.login;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.service.mutual.login.type.AdminLogin;
import engine.service.mutual.login.type.ClientLogin;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class LoginRequestHandler implements RequestHandler {
    private final EngineService engine;

    public LoginRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, AdminAlreadyLogged, UserAlreadyExists {
        if (request instanceof AdminLogin) {
            return new AdminLogin(engine).generateResponse(args);
        } else if (request instanceof ClientLogin) {
            return new ClientLogin(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}
