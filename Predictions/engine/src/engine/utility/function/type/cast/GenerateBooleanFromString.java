package engine.utility.function.type.cast;

import engine.utility.function.Function;

public class GenerateBooleanFromString implements Function {
    private final String argument;

    public GenerateBooleanFromString(String string) {
        this.argument = string;
    }

    @Override
    public String getName() {
        return "generateBooleanFromString";
    }

    @Override
    public Object run() {
        if (this.argument.equalsIgnoreCase("true")) {
            return true;
        } else if (this.argument.equalsIgnoreCase("false")) {
            return false;
        } else {
            return null;
        }
    }
}