package engine.verifier.type.action.type;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDCondition;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.ActionChecker;
import engine.verifier.type.action.VerifyAction;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.exception.type.InvalidSingularity;
import engine.verifier.type.action.type.condition.ConditionVerifier;
import engine.verifier.type.secondary.SecondaryEntityVerifier;

public class Conditional implements ActionChecker {
    @Override
    public void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException {
        if (action.getPRDSecondaryEntity() != null) {
            new SecondaryEntityVerifier().verifySecondaryEntity(action, entities);
        }

        ConditionVerifier conditionVerifier = new ConditionVerifier();
        if (action.getPRDCondition().getSingularity().equals("single")) {
            conditionVerifier.verifyCondition(action.getPRDCondition(), entities);
        } else if (action.getPRDCondition().getSingularity().equals("multiple")) {
            for (PRDCondition condition : action.getPRDCondition().getPRDCondition()) {
                conditionVerifier.verifyCondition(condition, entities);
            }
        } else {
            throw new ActionException(new InvalidSingularity(action.getPRDCondition().getSingularity()));
        }

        VerifyAction actionVerifier = new VerifyAction();

        if (action.getPRDThen() != null) {
            for (PRDAction thenAction : action.getPRDThen().getPRDAction()) {
                actionVerifier.checkAction(thenAction, entities, environments);
            }
        }

        if (action.getPRDElse() != null) {
            for (PRDAction elseAction : action.getPRDElse().getPRDAction()) {
                actionVerifier.checkAction(elseAction, entities, environments);
            }
        }
    }
}
