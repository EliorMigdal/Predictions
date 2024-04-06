package dto.response.mutual.executions;

import dto.DTO;

import java.util.ArrayList;

public class EntitiesNamesDTO implements DTO {
    private final ArrayList<String> entityNames = new ArrayList<>();

    public ArrayList<String> getEntityNames() {
        return entityNames;
    }

    public void addNewEntity(String entityName) {
        entityNames.add(entityName);
    }
}
