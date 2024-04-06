package engine.simulation.world.rule.action.expression.type.binary.exception;

public class DivideByZero extends Exception {
    @Override
    public String getMessage() {
        return "Cannot divide by zero!";
    }
}