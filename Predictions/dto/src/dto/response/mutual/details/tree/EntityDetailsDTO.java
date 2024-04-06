package dto.response.mutual.details.tree;

import dto.DTO;

import java.util.ArrayList;

public class EntityDetailsDTO implements DTO {
    private final String entityName;
    private final ArrayList<EntityPropertiesDetailsDTO> entityProperties = new ArrayList<>();

    public EntityDetailsDTO(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public ArrayList<EntityPropertiesDetailsDTO> getEntityProperties() {
        return entityProperties;
    }

    public void addProperty(EntityPropertiesDetailsDTO property) {
        entityProperties.add(property);
    }
}
