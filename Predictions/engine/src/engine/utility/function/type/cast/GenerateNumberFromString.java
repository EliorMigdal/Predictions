package engine.utility.function.type.cast;
import engine.utility.function.Function;

public class GenerateNumberFromString implements Function {
    private final String argument;

    public GenerateNumberFromString(String string) {
        this.argument = string;
    }

    @Override
    public String getName() {
        return "generateNumberFromString";
    }

    @Override
    public Object run() {
        Object output = null;

        if (argument.matches("-?\\d+(\\.\\d+)?")) {
            if (argument.contains(".")) {
                output = Float.parseFloat(argument);
            } else {
                output = Integer.parseInt(argument);
            }
        }

        return output;
    }
}