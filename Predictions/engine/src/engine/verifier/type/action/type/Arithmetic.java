package engine.verifier.type.action.type;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEnvironment;
import engine.verifier.type.action.ActionChecker;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.builder.GenerateEntityProperties;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.action.exception.type.PropertyNotFound;
import engine.verifier.type.action.type.arguments.VerifyArithmeticArgument;
import engine.verifier.type.secondary.SecondaryEntityVerifier;

import java.util.ArrayList;

public class Arithmetic implements ActionChecker {
    @Override
    public void checkAction(PRDAction action, PRDEntities entities, PRDEnvironment environments)
            throws ActionException {
        ArrayList<String> secondaryEntityProperties = null;
        if (action.getPRDSecondaryEntity() != null) {
            new SecondaryEntityVerifier().verifySecondaryEntity(action, entities);
            secondaryEntityProperties = new GenerateEntityProperties().generateEntityProperties(entities,
                    action.getPRDSecondaryEntity().getEntity());
        }

        ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);
        ArrayList<String> mainEntityProperties = new GenerateEntityProperties().generateEntityProperties(entities,
                action.getEntity());

        if (!entityNames.contains(action.getEntity())) {
            throw new ActionException(new EntityNotFound(action.getType(), action.getEntity()));
        }

        VerifyArithmeticArgument argumentVerify = new VerifyArithmeticArgument();

        if (action.getType().equals("increase") || action.getType().equals("decrease")) {
            if (!mainEntityProperties.contains(action.getProperty()) && secondaryEntityProperties != null
                    && !secondaryEntityProperties.contains(action.getProperty())) {
                throw new ActionException(new PropertyNotFound(action.getType(), action.getEntity(),
                        action.getProperty()));
            }
            argumentVerify.verifyArithmeticArgument(action, entities, action.getBy(), environments);

        } else if (action.getType().equals("calculation")) {
            if (!mainEntityProperties.contains(action.getResultProp()) && secondaryEntityProperties != null
                    && !secondaryEntityProperties.contains(action.getResultProp())) {
                throw new ActionException(new PropertyNotFound(action.getType(), action.getEntity(),
                        action.getResultProp()));
            }

            if (action.getPRDDivide() != null) {
                argumentVerify.verifyArithmeticArgument(action, entities, action.getPRDDivide().getArg1(),
                        environments);
                argumentVerify.verifyArithmeticArgument(action, entities, action.getPRDDivide().getArg2(),
                        environments);
            } else {
                argumentVerify.verifyArithmeticArgument(action, entities, action.getPRDMultiply().getArg1(),
                        environments);
                argumentVerify.verifyArithmeticArgument(action, entities, action.getPRDMultiply().getArg2(),
                        environments);
            }
        }
    }
}
