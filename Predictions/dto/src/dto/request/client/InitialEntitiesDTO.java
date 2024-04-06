package dto.request.client;


import dto.DTO;

public class InitialEntitiesDTO implements DTO {

    private final String entityName;

    public InitialEntitiesDTO(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
