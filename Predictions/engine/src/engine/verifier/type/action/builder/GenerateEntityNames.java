package engine.verifier.type.action.builder;

import engine.jaxb.generated.PRDEntities;
import engine.jaxb.generated.PRDEntity;

import java.util.ArrayList;

public class GenerateEntityNames {
    public ArrayList<String> generateEntityNames(PRDEntities entities) {
        ArrayList<String> entityNames = new ArrayList<>();

        for (PRDEntity entity : entities.getPRDEntity()) {
            entityNames.add(entity.getName());
        }

        return entityNames;
    }
}
