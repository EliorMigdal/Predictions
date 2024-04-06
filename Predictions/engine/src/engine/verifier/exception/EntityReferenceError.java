package engine.verifier.exception;

public class EntityReferenceError extends Exception {
    private String invalidRule;
    private String invalidAction;
    private String invalidEntityReferenceName;

    public EntityReferenceError(String invalidRule,String invalidAction,String invalidEntityReferenceName){
        setInvalidRule(invalidRule);
        setInvalidAction(invalidAction);
        setInvalidEntityReferenceName(invalidEntityReferenceName);
    }

    public String getInvalidRule() {
        return invalidRule;
    }

    public void setInvalidRule(String invalidRule) {
        this.invalidRule = invalidRule;
    }

    public String getInvalidAction() {
        return invalidAction;
    }

    public void setInvalidAction(String invalidAction) {
        this.invalidAction = invalidAction;
    }

    public String getInvalidEntityReferenceName() {
        return invalidEntityReferenceName;
    }

    public void setInvalidEntityReferenceName(String invalidEntityReferenceName) {
        this.invalidEntityReferenceName = invalidEntityReferenceName;
    }

    @Override
    public String getMessage() {
        return "Under rule: " + getInvalidRule() + " action: " + getInvalidAction() + " using undefined Entity: " + getInvalidEntityReferenceName();
    }
}