package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.service.mutual.details.propertycard.PropertyCardRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityPropertiesRequest implements PropertyCardRequest {
    private EngineService engine;

    public EntityPropertiesRequest() {
    }

    public EntityPropertiesRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        String entityName = (String) args[1];
        String propertyName = (String) args[2];
        Map<String, String> propertyMap = new LinkedHashMap<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().flatMap(simulation -> simulation.getPRDEntities().getPRDEntity()
                        .stream().filter(entity -> entity.getName().equals(entityName))
                        .findFirst()).flatMap(entity -> entity.getPRDProperties().getPRDProperty().stream()
                        .filter(property -> property.getPRDName().equals(propertyName))
                        .findFirst()).ifPresent(property -> {
                    propertyMap.put("Property type", property.getType());

                    if (property.getPRDRange() != null) {
                        propertyMap.put("Property range", property.getPRDRange().getFrom() + " to "
                                + property.getPRDRange().getTo());
                    }

                    propertyMap.put("Random init",
                            ((Boolean) property.getPRDValue().isRandomInitialize()).toString());
                });

        return propertyMap;
    }
}