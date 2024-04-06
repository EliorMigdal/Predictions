package engine.simulation.world.rule.action.expression.exception;
import java.util.ArrayList;

public class InvalidExpressions extends Exception {
    private ArrayList<String> expressions;

    public InvalidExpressions(ArrayList<String> expressions) {
        setExpressions(expressions);
    }

    public void setExpressions(ArrayList<String> expressions) {
        this.expressions = expressions;
    }

    @Override
    public String getMessage() {
        String result = "";
        for(String s : expressions) {
            result = result.concat(s + " ");
        }

        return "Invalid expressions received - " + result;
    }
}