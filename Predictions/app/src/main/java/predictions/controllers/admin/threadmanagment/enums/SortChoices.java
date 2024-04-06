package predictions.controllers.admin.threadmanagment.enums;

public enum SortChoices {
    AB("A-B"),
    RUN("Running"),
    QUEUE("Waiting"),
    FINISH("Finished");

    private final String value;
    SortChoices(String a) {
        this.value = a;
    }

    public String getValue() {
        return value;
    }
}
