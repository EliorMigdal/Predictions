package engine.service.mutual.details.treeview.type;

import engine.EngineService;
import engine.service.mutual.details.treeview.TreeViewRequest;

public class GetTerminationsNames implements TreeViewRequest {
    private EngineService engine;

    public GetTerminationsNames() {
    }

    public GetTerminationsNames(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return null;
    }
}
