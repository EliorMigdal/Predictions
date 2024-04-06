package engine.service.mutual.details.treeview;

import engine.EngineService;
import engine.service.Request;
import engine.service.handler.RequestHandler;
import engine.service.mutual.details.treeview.type.*;

public class TreeViewRequestHandler implements RequestHandler {
    private final EngineService engine;

    public TreeViewRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args) {
        if (request instanceof GetActivationsNames) {
            return new GetActivationsNames(engine).generateResponse(args);
        } else if (request instanceof GetActionNames) {
            return new GetActionNames(engine).generateResponse(args);
        } else if (request instanceof GetEntitiesNames) {
            return new GetEntitiesNames(engine).generateResponse(args);
        } else if (request instanceof GetEntityProperties) {
            return new GetEntityProperties(engine).generateResponse(args);
        } else if (request instanceof GetEnvironmentNames) {
            return new GetEnvironmentNames(engine).generateResponse(args);
        } else if (request instanceof GetRulesNames) {
            return new GetRulesNames(engine).generateResponse(args);
        } else if (request instanceof GetTerminationsNames) {
            return new GetTerminationsNames(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}