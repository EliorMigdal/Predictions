package engine.service.handler;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.admin.AdminRequest;
import engine.service.admin.handler.AdminRequestsHandler;
import engine.service.client.ClientRequest;
import engine.service.client.handler.ClientRequestsHandler;
import engine.service.exception.InitException;
import engine.service.mutual.MutualRequest;
import engine.service.mutual.handler.MutualRequestsHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.verifier.type.exception.XMLException;

public class MainRequestsHandler implements RequestHandler {
    private final EngineService engine;

    public MainRequestsHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, UserAlreadyExists, AdminAlreadyLogged, XMLException {
        if (request instanceof ClientRequest) {
            return new ClientRequestsHandler(engine).handleRequest(request, args);
        } else if (request instanceof AdminRequest) {
            return new AdminRequestsHandler(engine).handleRequest(request, args);
        } else if (request instanceof MutualRequest) {
            return new MutualRequestsHandler(engine).handleRequest(request, args);
        } else {
            return null;
        }
    }
}