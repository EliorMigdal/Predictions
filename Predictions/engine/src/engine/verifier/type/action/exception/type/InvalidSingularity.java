package engine.verifier.type.action.exception.type;

public class InvalidSingularity extends Exception {
    private final String singularity;

    public InvalidSingularity(String singularity) {
        this.singularity = singularity;
    }

    @Override
    public String getMessage() {
        return "Invalid condition singularity: " + singularity;
    }
}
