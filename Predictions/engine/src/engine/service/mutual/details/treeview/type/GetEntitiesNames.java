package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetEntitiesNames implements TreeViewRequest {
    private EngineService engine;

    public GetEntitiesNames() {
    }

    public GetEntitiesNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        ArrayList<String> entityNames = new ArrayList<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().ifPresent(simulation ->
                        simulation.getPRDEntities().getPRDEntity().forEach(
                                entity -> entityNames.add(entity.getName())
                        ));

        return entityNames;
    }
}
