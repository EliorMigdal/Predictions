package engine.verifier.type.secondary;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.action.exception.type.InvalidCount;
import engine.verifier.type.action.type.condition.ConditionVerifier;
import engine.verifier.type.expression.StringToNumber;

import java.util.ArrayList;

public class SecondaryEntityVerifier {
    public void verifySecondaryEntity(PRDAction action, PRDEntities entities)
            throws ActionException {
        ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);

        if (!entityNames.contains(action.getPRDSecondaryEntity().getEntity())) {
            throw new ActionException(new EntityNotFound(action.getType(),
                    action.getPRDSecondaryEntity().getEntity()));
        }

        if (action.getPRDSecondaryEntity().getPRDSelection() != null) {
            StringToNumber stringCaster = new StringToNumber();

            if (!stringCaster.isItANumber(action.getPRDSecondaryEntity().getPRDSelection().getCount())) {
                throw new ActionException(new InvalidCount(action.getPRDSecondaryEntity().getEntity(),
                        action.getPRDSecondaryEntity().getPRDSelection().getCount()));
            }

            if (action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition() != null) {
                ConditionVerifier conditionVerifier = new ConditionVerifier();
                conditionVerifier.verifyCondition(action.getPRDSecondaryEntity().getPRDSelection().getPRDCondition(),
                        entities);
            }
        }
    }
}
