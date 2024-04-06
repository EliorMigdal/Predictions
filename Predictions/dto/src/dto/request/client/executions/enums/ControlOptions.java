package dto.request.client.executions.enums;

public enum ControlOptions {
    PAUSE("Pause"),
    START("Start"),
    STOP("Stop");

    private final String value;

    ControlOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
