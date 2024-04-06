package engine.verifier.type.expression;

public class OperatorValidate {
    public boolean validate(String operator) {
        return operator.equals("bt") || operator.equals("lt")
                || operator.equals("=") || operator.equals("!=");
    }
}
