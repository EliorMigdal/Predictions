package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.jaxb.generated.PRDAction;
import engine.service.mutual.details.propertycard.PropertyCardRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActionPropertiesRequest implements PropertyCardRequest {
    private EngineService engine;

    public ActionPropertiesRequest() {
    }

    public ActionPropertiesRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return null;
    }
}