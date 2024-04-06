package engine.service.client.execute;

import engine.EngineService;
import engine.service.Request;
import engine.service.client.execute.type.GetInitialPopulations;
import engine.service.client.execute.type.GetInitialEnvironment;
import engine.service.client.execute.type.ResetEnvironmentInitMap;
import engine.service.handler.RequestHandler;

public class SimulationExecuteRequestHandler
        implements RequestHandler {
    private final EngineService engineService;

    public SimulationExecuteRequestHandler(EngineService engineService) {
        this.engineService = engineService;
    }

    @Override
    public Object handleRequest(Request request, Object... args) {
          if (request instanceof GetInitialEnvironment) {
              return new GetInitialEnvironment(engineService).generateResponse(args);
          } else if (request instanceof GetInitialPopulations){
              return new GetInitialPopulations(engineService).generateResponse(args);
          } else if (request instanceof ResetEnvironmentInitMap) {
              return new ResetEnvironmentInitMap(engineService).generateResponse(args);
          } else {
              return null;
          }
    }
}
