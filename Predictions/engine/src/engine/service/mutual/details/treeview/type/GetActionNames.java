package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetActionNames implements TreeViewRequest {
    private EngineService engine;

    public GetActionNames() {
    }

    public GetActionNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        String ruleName = (String) args[1];
        ArrayList<String> actionNames = new ArrayList<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().flatMap(simulation -> simulation.getPRDRules().getPRDRule().stream()
                        .filter(rule -> rule.getName().equals(ruleName))
                        .findFirst()).ifPresent(rule -> rule.getPRDActions().getPRDAction()
                        .forEach(action -> actionNames.add(action.getType())));

        return actionNames;
    }
}