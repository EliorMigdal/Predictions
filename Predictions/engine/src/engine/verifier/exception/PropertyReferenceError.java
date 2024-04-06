package engine.verifier.exception;

public class PropertyReferenceError extends Exception {
    private String invalidRule;
    private String invalidAction;
    private String relatedEntityName;
    private String invalidPropertyReferenceName;

    public PropertyReferenceError(String invalidRule, String invalidAction,
                                  String invalidPropertyReferenceName, String relatedEntityName) {
        setInvalidRule(invalidRule);
        setInvalidAction(invalidAction);
        setInvalidPropertyReferenceName(invalidPropertyReferenceName);
        setRelatedEntityName(relatedEntityName);
    }

    public String getRelatedEntityName() {
        return relatedEntityName;
    }

    public void setRelatedEntityName(String relatedEntityName) {
        this.relatedEntityName = relatedEntityName;
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

    public String getInvalidPropertyReferenceName() {
        return invalidPropertyReferenceName;
    }

    public void setInvalidPropertyReferenceName(String invalidPropertyReferenceName) {
        this.invalidPropertyReferenceName = invalidPropertyReferenceName;
    }

    @Override
    public String getMessage() {
        return "Under rule: " + getInvalidRule() + " action: " + getInvalidAction() + " defines with a property "
                + getInvalidPropertyReferenceName() + " not found in the related entity: " + getRelatedEntityName();
    }
}