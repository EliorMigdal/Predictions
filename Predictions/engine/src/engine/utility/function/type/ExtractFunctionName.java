package engine.utility.function.type;
import engine.utility.function.Function;

public class ExtractFunctionName implements Function {
    private String argument;

    public ExtractFunctionName(String argument) {
        setArgument(argument);
    }

    @Override
    public String getName() {
        return "extractFunctionName";
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    @Override
    public Object run() {
        StringBuilder functionName = new StringBuilder();
        boolean foundBrackets = false;

        for (char c : argument.toCharArray()) {
            if (c != '(') {
                functionName.append(c);
            }

            else {
                foundBrackets = true;
                break;
            }
        }


        if (foundBrackets) {
            return functionName.toString();
        } else {
            return null;
        }
    }
}