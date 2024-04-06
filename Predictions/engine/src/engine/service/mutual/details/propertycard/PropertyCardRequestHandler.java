package engine.service.mutual.details.propertycard;

import engine.EngineService;
import engine.service.Request;
import engine.service.handler.RequestHandler;
import engine.service.mutual.details.propertycard.type.*;

public class PropertyCardRequestHandler implements RequestHandler {
    private final EngineService engine;

    public PropertyCardRequestHandler(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object handleRequest(Request request, Object... args) {
        if (request instanceof ActionPropertiesRequest) {
            return new ActionPropertiesRequest(engine).generateResponse(args);
        } else if (request instanceof ActivationPropertyRequest) {
            return new ActivationPropertyRequest(engine).generateResponse(args);
        } else if (request instanceof TerminationPropertiesRequest) {
            return new TerminationPropertiesRequest(engine).generateResponse(args);
        } else if (request instanceof EnvironmentPropertyRequest) {
            return new EnvironmentPropertyRequest(engine).generateResponse(args);
        } else if (request instanceof EntityPropertiesRequest) {
            return new EntityPropertiesRequest(engine).generateResponse(args);
        } else if (request instanceof GridPropertiesRequest) {
            return new GridPropertiesRequest(engine).generateResponse(args);
        } else {
            return null;
        }
    }
}