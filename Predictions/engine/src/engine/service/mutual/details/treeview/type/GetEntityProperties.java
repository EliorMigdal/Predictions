package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetEntityProperties implements TreeViewRequest {
    private EngineService engine;

    public GetEntityProperties() {
    }

    public GetEntityProperties(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        String entityName = (String) args[1];
        ArrayList<String> entityProperties = new ArrayList<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().flatMap(simulation -> simulation.getPRDEntities().getPRDEntity().stream()
                        .filter(entity -> entity.getName().equals(entityName))
                        .findFirst()).ifPresent(entity -> entity.getPRDProperties().getPRDProperty()
                        .forEach(property -> entityProperties.add(property.getPRDName())));

        return entityProperties;
    }
}