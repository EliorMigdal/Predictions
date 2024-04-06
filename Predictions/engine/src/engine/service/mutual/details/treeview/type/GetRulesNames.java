package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetRulesNames implements TreeViewRequest {
    private EngineService engine;

    public GetRulesNames() {
    }

    public GetRulesNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        ArrayList<String> rulesNames = new ArrayList<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().ifPresent(simulation ->
                        simulation.getPRDRules().getPRDRule().forEach(
                                rule -> rulesNames.add(rule.getName())
                        ));

        return rulesNames;
    }
}
