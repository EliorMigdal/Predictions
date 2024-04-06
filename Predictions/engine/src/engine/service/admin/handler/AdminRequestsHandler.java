package engine.service.admin.handler;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.admin.management.ManagementRequest;
import engine.service.admin.management.ManagementRequestHandler;
import engine.service.admin.threadpool.ThreadPoolRequest;
import engine.service.admin.threadpool.ThreadPoolRequestHandler;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.verifier.type.exception.XMLException;

public class AdminRequestsHandler implements RequestHandler {
    private final EngineService engine;

    public AdminRequestsHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, UserAlreadyExists, AdminAlreadyLogged, XMLException {
        if (request instanceof ThreadPoolRequest) {
            return new ThreadPoolRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof ManagementRequest) {
            return new ManagementRequestHandler(engine).handleRequest(request, args);
        } else {
            return null;
        }
    }
}
