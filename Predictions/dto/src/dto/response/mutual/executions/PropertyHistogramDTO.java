package dto.response.mutual.executions;

import dto.DTO;

public class PropertyHistogramDTO implements DTO {
    private final String tick;
    private final String value;

    public PropertyHistogramDTO(String tick, String value) {
        this.tick = tick;
        this.value = value;
    }

    public String getTick() {
        return tick;
    }

    public String getValue() {
        return value;
    }
}
