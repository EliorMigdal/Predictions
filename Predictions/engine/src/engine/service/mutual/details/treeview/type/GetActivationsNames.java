package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

import java.util.ArrayList;

public class GetActivationsNames implements TreeViewRequest {
    private EngineService engine;

    public GetActivationsNames() {
    }

    public GetActivationsNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        ArrayList<String> activationNames = new ArrayList<>();
        activationNames.add("Probability");
        activationNames.add("Ticks");

        return activationNames;
    }
}