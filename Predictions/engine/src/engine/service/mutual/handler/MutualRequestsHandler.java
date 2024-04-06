package engine.service.mutual.handler;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.service.mutual.details.propertycard.PropertyCardRequest;
import engine.service.mutual.details.propertycard.PropertyCardRequestHandler;
import engine.service.mutual.details.treeview.TreeViewRequest;
import engine.service.mutual.details.treeview.TreeViewRequestHandler;
import engine.service.mutual.login.LoginRequest;
import engine.service.mutual.login.LoginRequestHandler;
import engine.service.mutual.logout.LogoutRequest;
import engine.service.mutual.logout.LogoutRequestHandler;
import engine.service.mutual.result.ResultRequest;
import engine.service.mutual.result.ResultRequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class MutualRequestsHandler implements RequestHandler {
    private final EngineService engine;

    public MutualRequestsHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws UserAlreadyExists, AdminAlreadyLogged, InitException, SimulationRuntimeException {
        if (request instanceof LoginRequest) {
            return new LoginRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof LogoutRequest) {
            return new LogoutRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof TreeViewRequest) {
            return new TreeViewRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof PropertyCardRequest) {
            return new PropertyCardRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof ResultRequest) {
            return new ResultRequestHandler(engine).handleRequest(request, args);
        } else {
            return null;
        }
    }
}
