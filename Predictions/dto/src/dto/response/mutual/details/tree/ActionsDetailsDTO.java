package dto.response.mutual.details.tree;

public class ActionsDetailsDTO {
    private final String actionName;

    public ActionsDetailsDTO(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
