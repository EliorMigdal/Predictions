package dto.response.mutual.executions;

import dto.DTO;

import java.util.ArrayList;

public class EntityPopulationDTO implements DTO {
    private String entityName = "";
    private final ArrayList<PopulationDTO> populationHistory = new ArrayList<>();

    public String getEntityName() {
        return entityName;
    }

    public ArrayList<PopulationDTO> getPopulationHistory() {
        return populationHistory;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void addPopulationItem(PopulationDTO newItem) {
        populationHistory.add(newItem);
    }
}
