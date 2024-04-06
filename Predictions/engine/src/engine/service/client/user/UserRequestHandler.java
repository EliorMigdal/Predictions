package engine.service.client.user;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.client.user.type.CreateNewRequest;
import engine.service.client.user.type.GetSpecificRequestExecutionDetails;
import engine.service.client.user.type.ViewAllRequests;
import engine.service.client.user.type.GetSimulationNamesList;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class UserRequestHandler implements RequestHandler {
    private final EngineService engine;

    public UserRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, AdminAlreadyLogged, UserAlreadyExists {
        if (request instanceof CreateNewRequest) {
            return new CreateNewRequest(engine).generateResponse(args);
        } else if (request instanceof ViewAllRequests) {
            return new ViewAllRequests(engine).generateResponse(args);
        } else if (request instanceof GetSimulationNamesList) {
            return new GetSimulationNamesList(engine).generateResponse(args);
        } else if (request instanceof GetSpecificRequestExecutionDetails) {
            return new GetSpecificRequestExecutionDetails(engine).generateResponse(args);
        }  else {
            return null;
        }
    }
}
