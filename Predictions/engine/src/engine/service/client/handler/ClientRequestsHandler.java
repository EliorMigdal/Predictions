package engine.service.client.handler;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.client.execute.SimulationExecuteRequest;
import engine.service.client.execute.SimulationExecuteRequestHandler;
import engine.service.client.init.SimulationInitRequest;
import engine.service.client.init.SimulationInitRequestHandler;
import engine.service.client.runtime.RuntimeRequest;
import engine.service.client.runtime.RuntimeRequestHandler;
import engine.service.client.user.UserRequest;
import engine.service.client.user.UserRequestHandler;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class ClientRequestsHandler implements RequestHandler {
    private final EngineService engine;

    public ClientRequestsHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, UserAlreadyExists, AdminAlreadyLogged {
        if (request instanceof SimulationInitRequest) {
            return new SimulationInitRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof RuntimeRequest) {
            return new RuntimeRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof SimulationExecuteRequest) {
            return new SimulationExecuteRequestHandler(engine).handleRequest(request, args);
        } else if (request instanceof UserRequest) {
            return new UserRequestHandler(engine).handleRequest(request, args);
        } else {
            return null;
        }
    }
}
