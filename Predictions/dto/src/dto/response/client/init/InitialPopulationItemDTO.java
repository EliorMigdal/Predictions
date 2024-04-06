package dto.response.client.init;

import dto.DTO;

public class InitialPopulationItemDTO implements DTO {
    private final String entityName;
    private final Integer initialPopulation;

    public InitialPopulationItemDTO(String entityName, Integer initialPopulation) {
        this.entityName = entityName;
        this.initialPopulation = initialPopulation;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getInitialPopulation() {
        return initialPopulation;
    }
}
