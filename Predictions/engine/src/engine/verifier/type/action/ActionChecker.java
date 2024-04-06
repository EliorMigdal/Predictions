package engine.verifier.type.action;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.exception.ActionException;

public interface ActionChecker {
    void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException;
}
