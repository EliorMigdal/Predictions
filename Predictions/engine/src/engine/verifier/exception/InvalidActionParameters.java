package engine.verifier.exception;

public class InvalidActionParameters extends Exception{
    private String actionName;
    private String invalidParameter;
    private String ruleName;

    public InvalidActionParameters(String actionName, String invalidParameter,String ruleName){
       setActionName(actionName);
        setInvalidParameter(invalidParameter);
        setRuleName(ruleName);
    }

    public String getActionName() {
        return actionName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getInvalidParameter() {
        return invalidParameter;
    }

    public void setInvalidParameter(String invalidParameter) {
        this.invalidParameter = invalidParameter;
    }

    @Override
    public String getMessage() {
        return "Under rule - " + getRuleName() + " found invalid argument - "
                + getInvalidParameter() +" in action - " + getActionName();
    }
}