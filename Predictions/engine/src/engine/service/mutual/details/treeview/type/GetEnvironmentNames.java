package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetEnvironmentNames implements TreeViewRequest {
    private EngineService engine;

    public GetEnvironmentNames() {
    }

    public GetEnvironmentNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        ArrayList<String> environmentNames = new ArrayList<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().ifPresent(simulation ->
                        simulation.getPRDEnvironment().getPRDEnvProperty().forEach(
                                property -> environmentNames.add(property.getPRDName())
                        ));

        return environmentNames;
    }
}
