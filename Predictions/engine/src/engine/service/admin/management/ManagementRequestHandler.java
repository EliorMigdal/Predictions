package engine.service.admin.management;

import engine.EngineService;
import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;
import engine.service.Request;
import engine.service.admin.management.type.*;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.verifier.type.exception.XMLException;

public class ManagementRequestHandler implements RequestHandler {
    private final EngineService engine;

    public ManagementRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException, AdminAlreadyLogged, UserAlreadyExists,
            XMLException {
        if (request instanceof ApproveClientRequest) {
            return new ApproveClientRequest(engine).generateResponse(args);
        } else if (request instanceof DeclineClientRequest) {
            return new DeclineClientRequest(engine).generateResponse(args);
        } else if (request instanceof GetClientRequests) {
            return new GetClientRequests(engine).generateResponse(args);
        } else if (request instanceof GetClientsNames) {
            return new GetClientsNames(engine).generateResponse(args);
        } else if (request instanceof GetSimulationsList) {
            return new GetSimulationsList(engine).generateResponse(args);
        } else if (request instanceof LoadSimulation) {
            return new LoadSimulation(engine).generateResponse(args);
        } else if (request instanceof GetSimulationsNames) {
            return new GetSimulationsNames(engine).generateResponse(args);
        } else if (request instanceof GetExecutedSimulations) {
            return new GetExecutedSimulations(engine).generateResponse(args);
        } else if (request instanceof GetNumOfClientRequests) {
            return new GetNumOfClientRequests(engine).generateResponse(args);
        } else if (request instanceof ExecutedSimulationInfo) {
            return new ExecutedSimulationInfo(engine).generateResponse(args);
        } else if (request instanceof AdminGetEntitiesInfo) {
            return new AdminGetEntitiesInfo(engine).generateResponse(args);
        } else if (request instanceof GetPopulationHistory) {
            return new GetPopulationHistory(engine).generateResponse(args);
        } else if (request instanceof AdminGeneratePropertyInfo) {
            return new AdminGeneratePropertyInfo(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}
