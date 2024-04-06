package engine.verifier.type.action.exception.type;

public class InvalidOperator extends Exception {
    private final String operator;
    private final String singularity;

    public InvalidOperator(String operator, String singularity) {
        this.operator = operator;
        this.singularity = singularity;
    }

    @Override
    public String getMessage() {
        return "Invalid operator " + operator + " in a " + singularity + " condition.";
    }
}
