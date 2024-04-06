package engine.utility.function.type;
import engine.utility.function.Function;

public class ExtractFunctionParameter implements Function {
    private String argument;

    public ExtractFunctionParameter(String argument) {
        setArgument(argument);
    }

    @Override
    public String getName() {
        return "extractFunctionParameters";
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    @Override
    public Object run() {
        StringBuilder currentArgument = new StringBuilder();
        boolean foundBrackets = false;

        for (char c : argument.toCharArray()) {
            if (c == '(') {
                foundBrackets = true;
            }

            else {
                if (foundBrackets) {
                    if (c == ',' || c == ')') {
                        break;
                    } else {
                        currentArgument.append(c);
                    }
                }
            }
        }

        if (!currentArgument.toString().isEmpty()) {
            return currentArgument.toString();
        } else {
            return null;
        }
    }
}