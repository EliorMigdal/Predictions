package engine.verifier.type.action.type.condition;

import engine.jaxb.generated.PRDCondition;
import engine.jaxb.generated.PRDEntities;
import engine.verifier.type.action.builder.GenerateEntityNames;
import engine.verifier.type.action.builder.GenerateEntityProperties;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.expression.ExpressionIdentifier;
import engine.verifier.type.action.exception.type.EntityNotFound;
import engine.verifier.type.action.exception.type.InvalidOperator;
import engine.verifier.type.action.exception.type.InvalidSingularity;
import engine.verifier.type.action.exception.type.PropertyNotFound;
import engine.verifier.type.expression.LogicalValidate;
import engine.verifier.type.expression.OperatorValidate;

import java.util.ArrayList;

public class ConditionVerifier {
    public void verifyCondition(PRDCondition condition, PRDEntities entities)
            throws ActionException {
        if (condition.getSingularity().equals("single")) {
            ExpressionIdentifier expressionID = new ExpressionIdentifier();
            ArrayList<String> entityNames = new GenerateEntityNames().generateEntityNames(entities);

            if (!entityNames.contains(condition.getEntity())) {
                throw new ActionException(new EntityNotFound("condition", condition.getEntity()));
            }

            if (!expressionID.identify(condition.getProperty())) {
                ArrayList<String> entityProperties = new GenerateEntityProperties().generateEntityProperties(
                        entities, condition.getEntity());

                if (!entityProperties.contains(condition.getProperty())) {
                    throw new ActionException(new PropertyNotFound("condition",
                            condition.getEntity(), condition.getProperty()));
                }
            }

            if (!(new OperatorValidate().validate(condition.getOperator()))) {
                throw new ActionException(new InvalidOperator(condition.getOperator(),
                        condition.getSingularity()));
            }
        } else if (condition.getSingularity().equals("multiple")) {
            LogicalValidate logicalValidate = new LogicalValidate();

            if (!logicalValidate.validate(condition.getLogical())) {
                throw new ActionException(new InvalidOperator(condition.getLogical(),
                        condition.getSingularity()));
            }

            ConditionVerifier conditionVerifier = new ConditionVerifier();
            for (PRDCondition subCondition : condition.getPRDCondition()) {
                conditionVerifier.verifyCondition(subCondition, entities);
            }
        } else {
            throw new ActionException(new InvalidSingularity(condition.getSingularity()));
        }
    }
}