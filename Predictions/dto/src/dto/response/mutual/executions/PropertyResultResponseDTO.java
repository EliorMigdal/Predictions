package dto.response.mutual.executions;

import dto.DTO;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyResultResponseDTO implements DTO {
    private final Map<Object, Integer> propertyHistogram = new LinkedHashMap<>();

    private Double consistencySum = 0D;
    private Double averageSum = 0D;
    private Integer instancesSum = 0;

    public String getPropertyConsistency() {
        if (instancesSum == 0D) {
            return "";
        } else {
            return String.format("%.3f", consistencySum / instancesSum);
        }
    }

    public String getPropertyAverage() {
        if (averageSum <= 0D || instancesSum == 0D) {
            return "";
        } else {
            return String.format("%.3f", averageSum / instancesSum);
        }
    }

    public Map<Object, Integer> getPropertyHistogram() {
        return propertyHistogram;
    }

    public void addNewData(Object value) {
        if (propertyHistogram.containsKey(value)) {
            propertyHistogram.put(value, propertyHistogram.get(value) + 1);
        } else {
            propertyHistogram.put(value, 1);
        }
    }

    public void incrementInstancesSum() {
        this.instancesSum++;
    }

    public void increaseAverageSum(Double averageValue) {
        this.averageSum += averageValue;
    }

    public void increaseConsistencySum(Double consistencyValue) {
        this.consistencySum += consistencyValue;
    }
}
