package engine.verifier.type.action.type;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.ActionChecker;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.action.exception.type.InvalidMode;
import engine.verifier.type.secondary.SecondaryEntityVerifier;

import java.util.ArrayList;

public class Replace implements ActionChecker {
    @Override
    public void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException {
        if (action.getPRDSecondaryEntity() != null) {
            new SecondaryEntityVerifier().verifySecondaryEntity(action, entities);
        }

        ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);

        if (!entityNames.contains(action.getKill())) {
            throw new ActionException(new EntityNotFound(action.getType(), action.getKill()));
        } else if (!entityNames.contains(action.getCreate())) {
            throw new ActionException(new EntityNotFound(action.getType(), action.getCreate()));
        } else if (!action.getMode().equals("derived") && !action.getMode().equals("scratch")) {
            throw new ActionException(new InvalidMode(action.getMode()));
        }
    }
}