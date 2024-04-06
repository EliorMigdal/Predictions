package predictions.views.Enums;

public enum SortBy {
    ALPHABETIC_AZ("Alphabetic A-Z"),
    ALPHABETIC_ZA("Alphabetic Z-A"),
    WAITING_STATUS("Waiting"),
    APPROVED_STATUS("Approved"),
    DECLINED_STATUS("Declined");

    private String label;

    SortBy(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
