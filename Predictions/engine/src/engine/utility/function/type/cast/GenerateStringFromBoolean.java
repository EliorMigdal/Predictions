package engine.utility.function.type.cast;
import engine.utility.function.Function;

public class GenerateStringFromBoolean implements Function {
    private final Boolean argument;

    public GenerateStringFromBoolean(Boolean argument) {
        this.argument = argument;
    }

    @Override
    public String getName() {
        return "stringFromBoolean";
    }

    @Override
    public Object run() {
        if (argument) {
            return "true";
        } else {
            return "false";
        }
    }
}