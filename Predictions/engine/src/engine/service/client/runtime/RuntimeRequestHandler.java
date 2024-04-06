package engine.service.client.runtime;

import engine.EngineService;
import engine.service.Request;
import engine.service.client.runtime.type.*;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class RuntimeRequestHandler implements RequestHandler {
    private final EngineService engine;

    public RuntimeRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws SimulationRuntimeException {
        if (request instanceof RunSimulation) {
            return new RunSimulation(this.engine).generateResponse(args);
        } else if (request instanceof GetCurrentRunningSimulation) {
            return new GetCurrentRunningSimulation(this.engine).generateResponse(args);
        } else if (request instanceof GetTicksPercentProgress) {
            return new GetTicksPercentProgress(this.engine).generateResponse(args);
        } else if (request instanceof GetTimePercentProgress) {
            return new GetTimePercentProgress(this.engine).generateResponse(args);
        } else if (request instanceof GetEntitiesCount) {
            return new GetEntitiesCount(this.engine).generateResponse(args);
        } else if (request instanceof IsSimulationRunning) {
            return new IsSimulationRunning(engine).generateResponse(args);
        } else if (request instanceof IsSimulationPaused) {
            return new IsSimulationPaused(engine).generateResponse(args);
        } else if (request instanceof IsSimulationProgressManually){
            return new IsSimulationProgressManually(engine).generateResponse(args);
        } else if (request instanceof IsSimulationHasError){
            return new IsSimulationHasError(engine).generateResponse(args);
        }else if (request instanceof IsSimulationQueued) {
            return new IsSimulationQueued(engine).generateResponse(args);
        } else if (request instanceof PauseSimulation) {
            return new PauseSimulation(engine).generateResponse(args);
        } else if (request instanceof ResumeSimulation) {
            return new ResumeSimulation(engine).generateResponse(args);
        } else if (request instanceof StopSimulation) {
            return new StopSimulation(engine).generateResponse(args);
        } else if (request instanceof GetSpecificEntityCount) {
            return new GetSpecificEntityCount(engine).generateResponse(args);
        } else if (request instanceof GetTicksProgress) {
            return new GetTicksProgress(engine).generateResponse(args);
        } else if (request instanceof  GetTimeProgress) {
            return  new GetTimeProgress(engine).generateResponse(args);
        } else if (request instanceof  ManualProgressSimulation) {
            return new ManualProgressSimulation(engine).generateResponse(args);
        } else if (request instanceof  CancelManualProgressSimulation) {
        return new CancelManualProgressSimulation(engine).generateResponse(args);
        } else {
            return null;
        }

    }
}