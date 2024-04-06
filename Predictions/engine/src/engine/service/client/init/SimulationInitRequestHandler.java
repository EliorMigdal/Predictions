package engine.service.client.init;

import engine.EngineService;
import engine.service.Request;
import engine.service.client.init.type.*;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;

public class SimulationInitRequestHandler implements RequestHandler {
    private final EngineService engine;

    public SimulationInitRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException {
        if (request instanceof ResetInitData) {
            return new ResetInitData(this.engine).generateResponse(args);
        } else if (request instanceof GetEnvironmentDetails) {
            return new GetEnvironmentDetails(this.engine).generateResponse(args);
        } else if (request instanceof RandomiseRestOfEnvironments) {
            return new RandomiseRestOfEnvironments(this.engine).generateResponse(args);
        } else if (request instanceof GetUninitializedPopulations) {
            return new GetUninitializedPopulations(this.engine).generateResponse(args);
        } else if (request instanceof InitializePopulation) {
            return new InitializePopulation(engine).generateResponse(args);
        } else if (request instanceof GetEnvInitializationMap) {
            return new GetEnvInitializationMap(engine).generateResponse(args);
        } else if (request instanceof SetEnvironmentValue) {
            return new SetEnvironmentValue(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}