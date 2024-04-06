package engine.verifier.type.expression;

public class ExtractParameter {
    public String extractParameter(String argument) {
        StringBuilder parameterBuilder = new StringBuilder();
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
                        parameterBuilder.append(c);
                    }
                }
            }
        }

        if (!parameterBuilder.toString().isEmpty()) {
            return parameterBuilder.toString();
        } else {
            return null;
        }
    }
}
