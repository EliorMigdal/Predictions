package engine.service.admin.management.type;

import engine.EngineService;
import engine.jaxb.generated.PRDWorld;
import engine.service.admin.management.ManagementRequest;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;

import java.util.stream.Collectors;

public class GetSimulationsNames implements ManagementRequest {
    private EngineService engine;

    public GetSimulationsNames() {
    }

    public GetSimulationsNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args)
            throws XMLException, InitException {
        return engine.getSimulationsCollection().keySet()
                .stream()
                .map(PRDWorld::getName)
                .collect(Collectors.toList());
    }
}
