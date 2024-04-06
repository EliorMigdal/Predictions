package engine.service.mutual.logout;

import engine.EngineService;
import engine.service.Request;
import engine.service.handler.RequestHandler;
import engine.service.mutual.logout.type.AdminLogout;
import engine.service.mutual.logout.type.UserLogout;

public class LogoutRequestHandler implements RequestHandler {
    private final EngineService engine;

    public LogoutRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args) {
        if (request instanceof AdminLogout) {
            return new AdminLogout(engine).generateResponse(args);
        } else if (request instanceof UserLogout) {
            return new UserLogout(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}
