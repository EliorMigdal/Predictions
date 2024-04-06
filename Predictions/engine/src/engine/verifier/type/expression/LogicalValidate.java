package engine.verifier.type.expression;

public class LogicalValidate {
    public boolean validate(String operator) {
        return operator.equals("or") || operator.equals("and");
    }
}
