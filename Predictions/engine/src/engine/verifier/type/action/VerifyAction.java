package engine.verifier.type.action;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.type.*;

public class VerifyAction {
    public void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException {
        if (action.getType().equals("replace")) {
            new Replace().checkAction(action, entities, environments);
        } else if (action.getType().equals("proximity")) {
            new Proximity().checkAction(action, entities, environments);
        } else if (action.getType().equals("increase") || action.getType().equals("decrease")
                || action.getType().equals("calculation")) {
            new Arithmetic().checkAction(action, entities, environments);
        } else if (action.getType().equals("set")) {
            new Set().checkAction(action, entities, environments);
        } else if (action.getType().equals("condition")) {
            new Conditional().checkAction(action, entities, environments);
        }
    }
}
