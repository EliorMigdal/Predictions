package dto.response.mutual.executions;

import dto.DTO;

public class EntityCountDTO implements DTO {
    private final String entityName;
    private final Integer entityCount;

    public EntityCountDTO(String entityName, Integer entityCount) {
        this.entityName = entityName;
        this.entityCount = entityCount;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getEntityCount() {
        return entityCount;
    }
}
