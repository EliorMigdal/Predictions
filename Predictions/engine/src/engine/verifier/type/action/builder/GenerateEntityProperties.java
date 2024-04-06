package engine.verifier.type.action.builder;

import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEntity;
import engine.jaxb.generated.PRDProperty;

import java.util.ArrayList;

public class GenerateEntityProperties {
    public ArrayList<String> generateEntityProperties(PRDEntities entities, String entityName) {
        ArrayList<String> entityProperties = new ArrayList<>();

        for (PRDEntity entity : entities.getPRDEntity()) {
            if (entity.getName().equals(entityName)) {
                for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                    entityProperties.add(property.getPRDName());
                }
            }
        }

        return entityProperties;
    }
}
