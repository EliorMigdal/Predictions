package engine.verifier.type.action.type.arguments;

import engine.jaxb.generated.*;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.builder.GenerateEntityProperties;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.expression.*;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.action.exception.type.EnvironmentNotFound;
import engine.verifier.type.action.exception.type.InvalidArithmeticArgument;
import engine.verifier.type.action.exception.type.PropertyNotFound;

import java.util.ArrayList;

public class VerifyArithmeticArgument {
    public void verifyArithmeticArgument(PRDAction action, PRDEntities entities,
                                         String argument, PRDEnvironment environments)
            throws ActionException {
        StringToNumber stringCaster = new StringToNumber();
        ExpressionIdentifier expressionID = new ExpressionIdentifier();
        SearchForProperty propertySearcher = new SearchForProperty();
        PRDProperty desiredProperty = null;

        if (propertySearcher.searchForProperty(entities, action.getEntity(), argument) != null) {
            desiredProperty = propertySearcher.searchForProperty(entities, action.getEntity(), argument);
        } else if (action.getPRDSecondaryEntity() != null &&
                propertySearcher.searchForProperty(entities,
                        action.getPRDSecondaryEntity().getEntity(), argument) != null) {
            desiredProperty = propertySearcher.searchForProperty(entities,
                    action.getPRDSecondaryEntity().getEntity(), argument);
        }

        if (desiredProperty != null) {
            if (!desiredProperty.getType().equals("decimal")
                    && !desiredProperty.getType().equals("float")) {
                throw new ActionException(new InvalidArithmeticArgument(action.getType(), argument));
            }
        } else if (!expressionID.identify(argument) && !stringCaster.isItANumber(argument)) {
            throw new ActionException(new InvalidArithmeticArgument(action.getType(), argument));
        } else if (expressionID.identify(argument)) {
            ExtractParameter extractParameter = new ExtractParameter();
            ExtractEntityName extractEntityName = new ExtractEntityName();
            ExtractPropertyName extractPropertyName = new ExtractPropertyName();
            ArrayList<String> environmentsNames = new ArrayList<>();
            ArrayList<String> entityPropertyNames;
            ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);
            String environmentName, entityName, propertyName;

            for (PRDEnvProperty envProperty : environments.getPRDEnvProperty()) {
                environmentsNames.add(envProperty.getPRDName());
            }

            if (argument.startsWith("environment")) {
                environmentName = extractParameter.extractParameter(argument);
                if (!environmentsNames.contains(environmentName)) {
                    throw new ActionException(new EnvironmentNotFound(action.getType(), environmentName));
                } else {
                    for (PRDEnvProperty envProperty : environments.getPRDEnvProperty()) {
                        if (envProperty.getPRDName().equals(environmentName)) {
                            if (!envProperty.getType().equals("decimal") && !envProperty.getType().equals("float")) {
                                throw new ActionException(new InvalidArithmeticArgument(action.getType(),
                                        environmentName));
                            }

                            break;
                        }
                    }
                }
            } else if (argument.startsWith("evaluate")) {
                entityName = extractEntityName.extractEntityName(extractParameter.extractParameter(argument));

                if (!entityNames.contains(entityName)) {
                    throw new ActionException(new EntityNotFound(action.getType(), action.getEntity()));
                }

                entityPropertyNames = new GenerateEntityProperties().generateEntityProperties(entities, entityName);
                propertyName = extractPropertyName.extractPropertyName(extractParameter.extractParameter(argument));

                if (!entityPropertyNames.contains(propertyName)) {
                    throw new ActionException(new PropertyNotFound(action.getType(), entityName, propertyName));
                }

                for (PRDEntity entity : entities.getPRDEntity()) {
                    if (entity.getName().equals(entityName)) {
                        for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                            if (property.getPRDName().equals(propertyName)) {
                                if (property.getType().equals("decimal") && property.getPRDName().equals("float")) {
                                    throw new ActionException(new InvalidArithmeticArgument(action.getType(),
                                            propertyName));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}