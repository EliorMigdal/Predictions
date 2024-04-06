package dto.response.mutual.executions;

import dto.DTO;

public class PopulationDTO implements DTO {
    private final Integer tickValue;
    private final Integer populationValue;

    public PopulationDTO(Integer tickValue, Integer populationValue) {
        this.tickValue = tickValue;
        this.populationValue = populationValue;
    }

    public Integer getTickValue() {
        return tickValue;
    }

    public Integer getPopulationValue() {
        return populationValue;
    }
}
