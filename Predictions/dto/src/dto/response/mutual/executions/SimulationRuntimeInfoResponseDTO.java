package dto.response.mutual.executions;

import dto.DTO;

public class SimulationRuntimeInfoResponseDTO implements DTO {
    private String ticksValue = "";
    private String ticksPercentage = "";
    private String timeValue = "";
    private String timePercentage = "";
    private String errorMessage = "";

    public String getTicksValue() {
        return ticksValue;
    }

    public String getTicksPercentage() {
        return ticksPercentage;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public String getTimePercentage() {
        return timePercentage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setTicksValue(String ticksValue) {
        this.ticksValue = ticksValue;
    }

    public void setTicksPercentage(String ticksPercentage) {
        this.ticksPercentage = ticksPercentage;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public void setTimePercentage(String timePercentage) {
        this.timePercentage = timePercentage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
