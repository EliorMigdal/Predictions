package engine.service.mutual.result;

import engine.EngineService;
import engine.service.Request;
import engine.service.mutual.result.type.GetPopulationHistory;
import engine.service.mutual.result.type.GetPropertyAverage;
import engine.service.mutual.result.type.GetPropertyConsistency;
import engine.service.mutual.result.type.GetPropertyHistogram;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class ResultRequestHandler implements RequestHandler {
    private final EngineService engineService;

    public ResultRequestHandler(EngineService engineService) {
        this.engineService = engineService;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException {
        if (request instanceof GetPopulationHistory) {
            return new GetPopulationHistory(engineService).generateResponse(args);
        } else if (request instanceof GetPropertyConsistency) {
            return new GetPropertyConsistency(engineService).generateResponse(args);
        } else if (request instanceof GetPropertyHistogram) {
            return new GetPropertyHistogram(engineService).generateResponse(args);
        } else if (request instanceof GetPropertyAverage) {
            return new GetPropertyAverage(engineService).generateResponse(args);
        } else {
            return null;
        }
    }
}
