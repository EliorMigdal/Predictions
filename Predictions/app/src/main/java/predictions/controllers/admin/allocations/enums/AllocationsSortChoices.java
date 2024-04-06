package predictions.controllers.admin.allocations.enums;

public enum AllocationsSortChoices {
    CLIENT("Client Name"),
    REQUEST("Request ID"),
    DATE("Request date"),
    SIMULATION("Simulation Name"),
    AMOUNT("Amount"),
    STATUS("Status");

    private final String value;

    AllocationsSortChoices(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
