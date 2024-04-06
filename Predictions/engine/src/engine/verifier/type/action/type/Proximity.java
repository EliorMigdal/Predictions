package engine.verifier.type.action.type;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.ActionChecker;
import engine.verifier.type.action.VerifyAction;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.secondary.SecondaryEntityVerifier;

import java.util.ArrayList;

public class Proximity implements ActionChecker {
    @Override
    public void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException {
        if (action.getPRDSecondaryEntity() != null) {
            new SecondaryEntityVerifier().verifySecondaryEntity(action, entities);
        }

        ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);
        VerifyAction actionVerifier = new VerifyAction();

        if (!entityNames.contains(action.getPRDBetween().getSourceEntity())) {
            throw new ActionException(new EntityNotFound(action.getType(),
                    action.getPRDBetween().getSourceEntity()));
        } else if (!entityNames.contains(action.getPRDBetween().getTargetEntity())) {
            throw new ActionException(new EntityNotFound(action.getType(),
                    action.getPRDBetween().getTargetEntity()));
        } else {
            if (action.getPRDActions() != null) {
                for (PRDAction thenAction : action.getPRDActions().getPRDAction()) {
                    actionVerifier.checkAction(thenAction, entities, environments);
                }
            }
        }
    }
}
