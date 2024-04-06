package engine.verifier.type.expression;

import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEntity;
import engine.jaxb.generated.PRDProperty;

public class SearchForProperty {
    public PRDProperty searchForProperty(PRDEntities entities, String entityName,
                                         String propertyName) {
        PRDEntity desiredEntity = null;

        for (PRDEntity entity : entities.getPRDEntity()) {
            if (entity.getName().equals(entityName)) {
                desiredEntity = entity;
                break;
            }
        }

        if (desiredEntity != null) {
            for (PRDProperty property : desiredEntity.getPRDProperties().getPRDProperty()) {
                if (property.getPRDName().equals(propertyName)) {
                    return property;
                }
            }

        }
        return null;
    }
}
