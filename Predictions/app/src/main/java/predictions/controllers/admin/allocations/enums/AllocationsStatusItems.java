package predictions.controllers.admin.allocations.enums;

public enum AllocationsStatusItems {
    ALL("All"),
    WAITING("Waiting"),
    APPROVED("Approved"),
    DECLINED("Declined");

    private final String value;

    AllocationsStatusItems(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
