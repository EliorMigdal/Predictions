package engine.service.mutual.result.type;

import engine.EngineService;
import engine.service.mutual.result.ResultRequest;
import engine.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GetPropertyHistogram implements ResultRequest {
    private EngineService engine;

    public GetPropertyHistogram() {
    }

    public GetPropertyHistogram(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String entityName = (String) args[3];
        String propertyName = (String) args[4];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        Map<Object, Integer> propertyHistogram = new HashMap<>();

        optionalSimulation.flatMap(simulation -> simulation.getWorld().getEntities().stream()
                .filter(entity -> entity.getEntityName().equals(entityName)).findFirst())
                .flatMap(entity -> entity.getEntityProperties().stream()
                .filter(property -> property.getPropertyName().equals(propertyName))
                        .findFirst()).ifPresent(property -> propertyHistogram.put(property
                .getPropertyValue(), propertyHistogram.getOrDefault(property.getPropertyValue(), 0) + 1));

        return propertyHistogram;
    }
}