package engine.service.admin.threadpool;

import engine.EngineService;
import engine.service.Request;
import engine.service.admin.threadpool.type.*;
import engine.service.exception.InitException;
import engine.service.handler.RequestHandler;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class ThreadPoolRequestHandler implements RequestHandler {
    private final EngineService engine;

    public ThreadPoolRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args)
            throws InitException, SimulationRuntimeException {
        if (request instanceof SetThreadPoolSize) {
            return new SetThreadPoolSize(engine).generateResponse(args);
        } else if (request instanceof GetNumOfBusyThreads) {
            return new GetNumOfBusyThreads(engine).generateResponse(args);
        } else if (request instanceof GetNumOfFinishedTasks) {
            return new GetNumOfFinishedTasks(engine).generateResponse(args);
        } else if (request instanceof GetThreadQueueSize) {
            return new GetThreadQueueSize(engine).generateResponse(args);
        } else if (request instanceof GetClientsUsage) {
            return new GetClientsUsage(engine).generateResponse(args);
        } else if (request instanceof GetThreadPoolStatus) {
            return new GetThreadPoolStatus(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}
