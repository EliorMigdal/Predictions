package engine.service.mutual.details.treeview;

import engine.service.mutual.MutualRequest;

public interface TreeViewRequest extends MutualRequest {
    Object generateResponse(Object... args);
}